const fs = require("fs");
const util = require("util");

const input = fs
  .readFileSync("./input.txt", "utf8")
  .trim()
  .split(" ")
  .map(s => parseInt(s));

const sum = array => array.reduce((a, b) => a + b, 0);

const getTree = (input, currentIndex = 0) => {
  const numChildren = input[currentIndex++];
  const numMeta = input[currentIndex++];

  if (numChildren === 0) {
    const meta = input.slice(currentIndex, currentIndex + numMeta);
    const metaSum = sum(meta);
    return [{
      meta,
      value: metaSum,
      metaSum
    }, currentIndex + numMeta];
  }

  const children = [];
  let childMetaSum = 0, child;
  for (let i = 1; i <= numChildren; i++) {
    [child, currentIndex] = getTree(input, currentIndex);
    children.push(child);
    childMetaSum += child.metaSum;
  }

  const meta = input.slice(
    currentIndex,
    currentIndex + numMeta
  );

  return [{
    meta,
    value: sum(
      meta
        .filter(meta => meta > 0 && meta <= children.length)
        .map(validMeta => children[validMeta - 1].value)
    ),
    metaSum: sum(meta) + childMetaSum,
    children
  }, currentIndex + numMeta];
};

const [tree] = getTree(input);

const answer1 = tree.metaSum;
console.log("Answer to Part 1 of Day 8: ", answer1);

const answer2 = tree.value;
console.log("Answer to Part 2 of Day 8: ", answer2);

// console.log(
//   util.inspect(getTree([2, 3, 0, 3, 10, 11, 12, 1, 1, 0, 1, 99, 2, 1, 1, 2]), {
//     showHidden: false,
//     depth: null
//   })
// );
