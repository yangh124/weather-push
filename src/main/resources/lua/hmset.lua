local key = KEYS[1]
local fieldCount = ARGV[1]
local expired = ARGV[2]
for i = 3, fieldCount, 2 do
    redis.pcall('HSET', key, ARGV[i], ARGV[i + 1])
end
redis.pcall('EXPIRE', key, expired)