const fs = require("fs");

const map = fs
  .readFileSync("./input.txt", "utf8")
  .trim()
  .replace(/\n/g, "").split("");

const MAP_SIZE = Math.floor(Math.sqrt(map.length));

const elves = [], goblins = [];
for (let i = 0; i < map.length; i++) {
  if (map[i] === "E") elves.push({ location: i, hp: 200 });
  if (map[i] === "G") goblins.push({ location: i, hp: 200 });
}

const locationToCoord = location => [
  location % MAP_SIZE,
  Math.floor(location / MAP_SIZE)
];

const coordToLocation = ([x, y]) => MAP_SIZE * y + x;

const isAdjacent = ([x1, y1], [x2, y2]) => Math.abs(x1 - x2) + Math.abs(y1 - y2) === 1

const toAttack = ({ location }, enemies) => {
  const unitCoord = locationToCoord(location);
  return enemies.filter(enemy => {
    const enemyCoord = locationToCoord(enemy.location);
    return isAdjacent(unitCoord, enemyCoord);
  }).sort((a, b) => a.hp !== b.hp ? a.hp - b.hp : a.location - b.location)[0]
}

const isDead = ({ hp }) => hp <= 0
const isNotDead = unit => !isDead(unit)

const getFirstMoveToNearest = (map, unit, enemies) => {
  let frontier = [{ path: [], location: unit.location }]
  const seen = new Set([unit.location])
  const enemyLocations = enemies.map(({ location }) => location)

    while (!enemyLocations.some(e => seen.has(e))) {
        const newFrontier = [];
        for (let location of frontier) {
            const [x, y] = locationToCoord(location.location);
            const adjacentLocations = [[x, y - 1], [x - 1, y], [x + 1, y], [x, y + 1]]
                .filter(coord => isAdjacent([x, y], coord))
                .map(coordToLocation)
                .filter(adj => (map[adj] !== "#") && (map[adj] !== map[unit.location]))
                .filter(adj => !seen.has(adj));

            adjacentLocations.forEach(adj => seen.add(adj));
            adjacentLocations.forEach(adj => newFrontier.push({ path: [...location.path, location.location], location: adj }))
        }

        frontier = newFrontier;
        if (frontier.length === 0) {
            return undefined
        }
    }       

    const candidates = frontier.filter(({ location }) => enemyLocations.includes(location))
    
    return candidates.sort((a, b) => a.path[a.path.length - 1] - b.path[b.path.length - 1])[0].path[1]
}

const updateLocation = (map, unit, enemies) => {
  const firstMove = getFirstMoveToNearest(map, unit, enemies);
  if (firstMove) {
    map[firstMove] = map[unit.location]
    map[unit.location] = '.'
    unit.location = firstMove;
  }
}

const nextRound = (map, elves, goblins) => {
  const units = [...elves, ...goblins].sort((a, b) => a.location - b.location);
  for (let unit of units) {
    if (isDead(unit)) continue;
    const enemies = elves.includes(unit) ? goblins : elves
    updateLocation(map, unit, enemies);
    
    const enemyToAttack = toAttack(unit, enemies);
    if (enemyToAttack) {
      enemyToAttack.hp -= 3
      if(isDead(enemyToAttack)) {
        map[enemyToAttack.location] = '.'
      }
    }

    elves = elves.filter(isNotDead)
    goblins = goblins.filter(isNotDead)
  }
  return [elves, goblins]
}

const simulate = (map, elves, goblins) => {
  let i = -1;
  while (elves.length && goblins.length) {
    [elves, goblins] = nextRound(map, elves, goblins)
    i++;
    // if (i === 5)break;
    console.log(i)
    // console.log(elves.length, goblins.length)
  }
//   console.log(i)
  const e = [...elves, ...goblins].filter(isNotDead);
  // console.log(e.reduce((sum, { hp }) => sum + hp, 0))
  // console.log(e)
  return (i + 1) * e.reduce((sum, { hp }) => sum + hp, 0); // i + 1 only if last move
}

const printMap = map => {
  let result = "";
  for (let i = 0; i < map.length; i++) {
    if (i % Math.sqrt(map.length) === 0) result += "\n";
    result += map[i];
  }
  console.log(result);
}

// printMap(map)
const answer1 = simulate(map, elves, goblins)
// printMap(map)

console.log("Answer to Part 1 of Day 15: ", answer1);

const answer2 = undefined;
console.log("Answer to Part 2 of Day 15: ", answer2);
