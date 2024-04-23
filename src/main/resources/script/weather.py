import os
import sys
import time

import pymysql
import redis
import requests
from lxml import etree
from xpinyin import Pinyin

# 服务器ip
server_ip = '127.0.0.1'
path = os.path.dirname(__file__)

# 爬取天气url
weather_list = []

maketrans = {
    '省': '',
    '市': '',
    '区': '',
    '县': ''
}

headers = {
    'user-agent':
        'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4692.71 '
        'Safari/537.36 '
}


# 解析天气数据
def parse_weather_text(content):
    res = etree.HTML(content)
    desc = res.xpath('//div[@class="current-abstract"]/text()')[0]
    return desc.strip()


def save_redis(weather, connect, date_str):
    url = weather.get('url')
    tagid = weather.get('tagid')
    name = weather.get('name')
    # 获取天气
    weather_content = requests.get(url=url, headers=headers).text
    weather_msg = parse_weather_text(weather_content)
    weather_msg = name + ':' + weather_msg
    print(weather_msg)
    connect.set(date_str + ":" + str(tagid), weather_msg, ex=36000)


def select_tag_from_msql():
    conn = pymysql.connect(host=server_ip, port=30306, user='root', password='root', db='weather')
    sql = "SELECT tag_id,location_name,code FROM sys_location;"
    try:
        with conn.cursor() as cursor:
            cursor.execute(sql)
            result = cursor.fetchall()
            for row in result:
                tagid = row[0]
                name = convert_tag_name(row[1])
                code = row[2]
                py = Pinyin().get_pinyin(name, '')
                url = 'https://www.qweather.com/weather/' + py + '-' + code + '.html'
                obj = {
                    "tagid": tagid,
                    "name": name,
                    "code": code,
                    "url": url
                }
                weather_list.append(obj)

    except Exception as e:
        print(e)
        exit()


# 替换省、市、区、县为空
def convert_tag_name(old_name):
    new_name = old_name.translate(str.maketrans(maketrans))
    return new_name


'''
<json> = json.dumps(<dict>)
<dict> = json.loads(<json>)
'''
if __name__ == '__main__':
    select_tag_from_msql()
    pool = redis.ConnectionPool(host=server_ip, port=30919, db=0,password='root')

    date_str = time.strftime("%Y-%m-%d", time.localtime())

    if len(sys.argv) > 1:
        # 遍历参数
        for i in sys.argv[1:]:
            index = int(i) - 1
            obj = weather_list[index]
            connect = redis.Redis(connection_pool=pool)
            save_redis(obj, connect, date_str)
    else:
        for obj in weather_list:
            connect = redis.Redis(connection_pool=pool)
            save_redis(obj, connect, date_str)
