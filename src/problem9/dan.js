const remove = node => {
  node.right.left = node.left
  node.left.right = node.right
  return node.right
}

const insertAfter = (node, value) => {
  const newNode = { value, left: node, right: node.right }
  node.right.left = newNode
  node.right = newNode
  return newNode
}

const calculateHighScore = (numPlayers, numMarbles) => {
  if (numMarbles < 23) return 0;

  const ZERO = { value: 0 }
  ZERO.left = ZERO
  ZERO.right = ZERO
  insertAfter(ZERO, 1)
  insertAfter(ZERO, 2)

  let currentPlayer = 3;
  let currentMarble = ZERO.right;
  const scores = {};

  for (let i = 3; i <= numMarbles; i++) {
    if (i % 23 === 0) {
      scores[currentPlayer] = (scores[currentPlayer] || 0) + i;
      let toDelete = currentMarble;
      for (let j = 0; j < 7; j++) {
        toDelete = toDelete.left;
      }
      currentMarble = remove(toDelete);
      scores[currentPlayer] += toDelete.value;
    } else {
      const left = currentMarble.right;
      currentMarble = insertAfter(left, i);
    }
    currentPlayer = (currentPlayer % numPlayers) + 1;
  }
  return Math.max(...Object.values(scores));
};


const answer1 = calculateHighScore(30, 5807);
console.log("Answer to Part 1 of Day 9: ", answer1);

const start = new Date().getTime();
const answer2 = calculateHighScore(432, 71019 * 100);
console.log("Answer to Part 2 of Day 9: ", answer2);
console.log(new Date().getTime() - start)