const fs = require("fs");

const instructions = fs
  .readFileSync("./input.txt", "utf8")
  .trim()
  .split("\n")
  .map(line => [line[5], line[36]]);

const steps = [
  ...new Set(
    instructions.reduce((steps, instruction) => [...instruction, ...steps])
  )
];

const stepOrders = instructions.reduce((orders, [before, after]) => {
  orders[after] ? orders[after].push(before) : (orders[after] = [before]);
  return orders;
}, {});

let completeSteps = "";
while (completeSteps.length !== steps.length) {
  const possibleSteps = steps.filter(
    step =>
      !completeSteps.includes(step) &&
      (!stepOrders[step] ||
        !stepOrders[step].filter(step => !completeSteps.includes(step)).length)
  );
  const nextStep = possibleSteps.sort()[0];
  completeSteps += nextStep;
}

const answer1 = completeSteps;
console.log("Answer to Part 1 of Day 7: ", answer1);

const availableWorkers = 5;
completeSteps = "";
let currentSteps = [];
let time = 0;
while (completeSteps.length !== steps.length) {
  const possibleSteps = steps.filter(
    step =>
      !completeSteps.includes(step) &&
      !currentSteps.map(([s]) => s).includes(step) &&
      (!stepOrders[step] ||
        !stepOrders[step].filter(step => !completeSteps.includes(step)).length)
  );
  possibleSteps.sort();
  currentSteps.push(
    ...possibleSteps
      .slice(
        0,
        Math.min(availableWorkers - currentSteps.length, possibleSteps.length)
      )
      .map(step => [step, step.charCodeAt() - 4])
  );
  currentSteps.sort(([, timeLeft1], [, timeLeft2]) => timeLeft1 - timeLeft2);
  const [completeStep, timeToDeduct] = currentSteps.shift();
  completeSteps += completeStep;
  currentSteps = currentSteps.map(([s, t]) => [s, t - timeToDeduct]);
  time += timeToDeduct;
}

const answer2 = time;
console.log("Answer to Part 2 of Day 7: ", answer2);
