local key = tostring(KEYS[1])
local fieldCount = tonumber(ARGV[1])
local expired = tonumber(ARGV[2])
for i = 3, fieldCount, 2 do
    redis.call('HSET', key, tostring(ARGV[i]), tostring(ARGV[i + 1]))
end
redis.call('EXPIRE', key, expired)