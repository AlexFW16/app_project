# App to test simple ciphers


### How is MVC implemented?

- Model is all the cipher stuff that is calculated in the background
- When a user goes to the page of a certain cipher, enters parameters,
  the Controller sends the information to the model class, the model class
  encrypts/decrypts with given params and the controller returns the result


### Notes

To center text fields inside a **constraint layout**, you can use:

'''
app:layout_constraintLeft_toLeftOf="parent"
app:layout_constraintRight_toRightOf="parent"
app:layout_constraintTop_toTopOf="parent"
app:layout_constraintBottom_toBottomOf="parent"
'''

To center in vertical_linear_layout: android:layout_gravity: "center"

src: https://stackoverflow.com/questions/43143468/how-to-center-the-elements-in-constraintlayout

#### Plan
1. Make the necessary XML layouts
2. Create background logic (Model)
3. Make it functional: Implement Controller
4. Small stuff, bug fixing, etc.
5. Make it look good
6. add more content

##### TODO
- Template activity for standard ciphers, must be adapted depending on which cipher is used at runtime
- Make input fields, etc. look nice
- Make 2 colour schemes, one for decrypting and one for encrypting


##### Layouts
- One vertical linear layout, with horizontal linear layouts (2-3 image buttons for ciphers)
- each horizontal layout: wrap_content for vertical to not push away to other horizontals
- use match_parent for horizontal, to fully cover the screen
- the vertical layout: width with match parent to cover full screen
  height to **0dp**, which means "match_constraints"
- Rounded corners: https://stackoverflow.com/questions/21633637/rounded-corners-android-image-buttons
- 
