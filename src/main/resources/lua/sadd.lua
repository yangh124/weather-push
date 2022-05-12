local key = KEYS[1]
local fieldCount = ARGV[1]
local expired = ARGV[2]
for i = 3, fieldCount, 1 do
    redis.pcall('SADD', key, ARGV[i])
end
redis.pcall('EXPIRE', key, expired)