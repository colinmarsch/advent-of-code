lines = []
with open("Day02_input.txt") as file:
    for line in file:
        lines.append(line.rstrip())

def part1(lines):
  sum = 0
  for line in lines:
     possible = True
     parts = line.split(":")
     id = int(parts[0].split()[1])
     sets = parts[1].split(";")
     for set in sets:
        cubes = set.split(",")
        for choice in cubes:
           colour = choice.split()[1]
           count = int(choice.split()[0])
           if colour == "red" and count > 12:
              possible = False
           if colour == "blue" and count > 14:
              possible = False
           if colour == "green" and count > 13:
              possible = False
     if possible:
        sum += id
  return sum

def part2(lines):
  sum = 0
  for line in lines:
     greenMin = 0
     blueMin = 0
     redMin = 0
     parts = line.split(":")
     sets = parts[1].split(";")
     for set in sets:
        cubes = set.split(",")
        for choice in cubes:
           colour = choice.split()[1]
           count = int(choice.split()[0])
           if colour == "red" and count > redMin:
              redMin = count
           if colour == "blue" and count > blueMin:
              blueMin = count
           if colour == "green" and count > greenMin:
              greenMin = count
     sum += greenMin * blueMin * redMin
  return sum

print("Part 1: " + str(part1(lines)))
print("Part 2: " + str(part2(lines)))