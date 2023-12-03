lines = []
with open("Day03_input.txt") as file:
    for line in file:
        lines.append(line.rstrip())

visited = set()

def part1(lines):
    sum = 0
    grid = [list(line) for line in lines]
    visited = set()

    for r, row in enumerate(grid):
        for c, char in enumerate(row):
            if (r,c) not in visited and char.isdigit():
                if findsSymbol(r, c, grid):
                    result = computeNumber(r, c, grid)
                    sum += result
    return sum 

def part2(lines):
    sum = 0
    grid = [list(line) for line in lines]
    visited = set()
    gears = {}

    for r, row in enumerate(grid):
        for c, char in enumerate(row):
            if (r,c) not in visited and char.isdigit():
                found, indexSet = findGear(r, c, grid)
                if found:
                    computeNumber(r, c, grid) # to mark the whole number visited
                    for x, y in indexSet:
                        gears.setdefault((x,y), set()).add((r,c))

    for key in gears:
        if len(gears[key]) == 2:
            power = 1
            for r,c in gears[key]:
                power *= computeNumber(r, c, grid)
            sum += power

    return sum 

def findsSymbol(r, c, grid, checkNum=True):
    if (r,c) not in visited and len(grid) > r and len(grid[r]) > c and r >= 0 and c >= 0:
        if checkNum and grid[r][c].isdigit():
            visited.add((r,c))
            result = findsSymbol(r-1, c-1, grid, False) or findsSymbol(r-1, c, grid, False) or findsSymbol(r, c-1, grid) or findsSymbol(r+1, c+1, grid, False) or findsSymbol(r+1, c, grid, False) or findsSymbol(r, c+1, grid) or findsSymbol(r+1, c-1, grid, False) or findsSymbol(r-1, c+1, grid, False)
            return result
        else:
            return grid[r][c] != '.'
    return False

def findGear(r, c, grid, checkNum=True):
    if (r,c) not in visited and len(grid) > r and len(grid[r]) > c and r >= 0 and c >= 0:
        if checkNum and grid[r][c].isdigit():
            visited.add((r,c))
            r1 = findGear(r-1, c-1, grid, False)
            r2 = findGear(r-1, c, grid, False)
            r3 = findGear(r, c-1, grid)
            r4 = findGear(r+1, c+1, grid, False)
            r5 = findGear(r+1, c, grid, False)
            r6 = findGear(r, c+1, grid)
            r7 = findGear(r+1, c-1, grid, False)
            r8 = findGear(r-1, c+1, grid, False)
            f1, i1 = r1
            f2, i2 = r2
            f3, i3 = r3
            f4, i4 = r4
            f5, i5 = r5
            f6, i6 = r6
            f7, i7 = r7
            f8, i8 = r8
            result = (f1 or f2 or f3 or f4 or f5 or f6 or f7 or f8, i1 | i2 | i3 | i4 | i5 | i6 | i7 | i8)
            return result
        else:
            result = {}
            if grid[r][c] == '*':
                result = {(r, c)}
            else:
                result = set()
            return (grid[r][c] == '*', result)
    return (False, set())

def computeNumber(r, c, grid):
    num = ""
    while (len(grid[r]) > c and grid[r][c].isdigit()):
        visited.add((r,c))
        num += grid[r][c]
        c += 1
    return int(num)

print("Part 1: " + str(part1(lines)))
visited = set()
print("Part 2: " + str(part2(lines)))

