keys, locks = [], []

for thing in map(str.split, open('input.txt').read().split('\n\n')):
    if thing[0][0] == '.':
        keys.append([col.count('#')-1 for col in zip(*thing)])
    if thing[0][0] == '#':
        locks.append([col.count('#')-1 for col in zip(*thing)])

print(sum(all(k[col]+l[col] <= 5 for col in range(5))
              for k in keys for l in locks))