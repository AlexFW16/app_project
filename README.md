# App to test simple ciphers


### How is MVC implemented?

- Model is all the cipher stuff that is calculated in the background
- When a user goes to the page of a certain cipher, enters parameters,
  the Controller sends the information to the model class, the model class
  encrypts/decrypts with given params and the controller returns the result


### Notes

To center text fields inside a constraint layout, you can use:

'''xml
app:layout_constraintLeft_toLeftOf="parent"
app:layout_constraintRight_toRightOf="parent"
app:layout_constraintTop_toTopOf="parent"
app:layout_constraintBottom_toBottomOf="parent"
'''

src: https://stackoverflow.com/questions/43143468/how-to-center-the-elements-in-constraintlayout

