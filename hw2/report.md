## Report for HW 2

### Assumption
- While scanning, we would treat all comments as a single white space and ignore it just like the Pascal scanner provided by professor. 

- Since comments would be ignored, those symbols including "//" and "/ * /" would never be read as special tokens in the program.

### Cool things we have done in our program 
- Our program would directly print out literal values such as "\n" and "\t" rather than escape those tokens. So they will be printed as a new line and a new tab on the console output. 

- For the implementation of characters, we apply a string including all possible escape characters. This helps our code become more concise because it's used in both *JavaCharacterToken* and *JavaStringToken* classes. Therefore, we don't have to write duplicated code for handling escape characters. s
