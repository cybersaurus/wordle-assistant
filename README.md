### Wordle Assistant
Created as an experiment to play with [Scala CLI]([https://scala-cli.virtuslab.org/]).

To make the script executable:
```shell
chmod +x ./wa.sc
```

 Or run via:
```shell
scala-cli ./wa.sc
```

Run the unit tests:
```shell
scala-cli test ./scala
```

#### Usage
Type in your guess and score. A score is represented as:
- `G` - Green letter. i.e. correct position for a letter in the word
- `Y` - Yellow letter. i.e. wrong position for a letter in the word
- `W` - White letter. i.e. a letter not contained in the word.

After each line of input, the Wordle Assistant will print out how many potential words match
against your input and score.
Next, either repeat with your next guess, or hit return to see a sample of words that match
your guesses and scores.

#### Example
Given the word to be guessed is: `PINEY`

```shell
./wa.sc

Enter guess [1]: RAISE WWYWY
Matches: [184]
Enter guess [2]: TIMED WGWGW
Matches: [21]
Enter guess [3]: LINEN WGGGW
Matches: [1]
Enter guess [4]: 
1 matches:
PINEY
```
