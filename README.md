# App to test simple ciphers


### How is MVC implemented?

- Model is all the cipher stuff that is calculated in the background
- When a user goes to the page of a certain cipher, enters parameters,
  the Controller sends the information to the model class, the model class
  encrypts/decrypts with given params and the controller returns the result
