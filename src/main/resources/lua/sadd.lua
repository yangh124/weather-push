local key = tostring(KEYS[1])
local fieldCount = tonumber(ARGV[1])
local expired = tonumber(ARGV[2])
for i = 3, fieldCount, 1 do
    redis.call('SADD', key, tostring(ARGV[i]))
end
redis.call('EXPIRE', key, expired)