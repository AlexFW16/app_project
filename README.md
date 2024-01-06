# 0xCrypt
**Try out how the basic ciphers work**

### What it must contain
- [ ] Comprise of good quality UI with at least two fragments separating all the components.
-  [ ] Use the activity life cycle states through its various methods (such as onStart, onPause, onDestroy,
etc.).
- [ ] Utilise at least one: device location/ Google Maps/ Media player/ Camera/ microphone/ any native
android apps, service, sensor, or sensor API.
- [ ] Use persistence data storage (e.g. SQLite, ROOM/ Firebase/Firestore, etc.).
- [ ] Use MVC or similar (java-based frameworks such as MVP, MVVM, etc.) framework.


### TODO
- [ ] Template activity for standard ciphers, must be adapted depending on which cipher is used at runtime
- [ ] Make input fields, etc. look nice
- [ ] Make 2 colour schemes, one for decrypting and one for encrypting 
- [ ] Add another fragment (bottom) that is used to display more detailed information (xor: binary code, ...)
- [ ]Weird that the fragments change their id's, probably bc they are the same and get overriden on change or smth? 
  -> Just use the same id for the same buttons etc.
- [ ] Error messages for wrong input in encryption
- [ ] Change binary mode to switch instead of button
- [ ] Debug XOR cipher
- [ ] (We could also add the option to save inputs and have a history -> Lot of work, for now only the last used snapshot)
- [ ] Add switch button (output text and msg)
- [ ] Descriptions/how to use for all ciphers
- [ ]Maybe issues when restoring data with isBinary in XOR?
- [ ] (Add key/password generator)


#### Firebase Realtime Database
- URL must be changed in the google-services.json
### How is MVC implemented?

- Model is all the cipher stuff that is calculated in the background
- When a user goes to the page of a certain cipher, enters parameters,
  the Controller sends the information to the model class, the model class
  encrypts/decrypts with given params and the controller returns the result


### Case Study / Problem
Help people learn/understand how cryptography works in an intuitive and easy way.



## Notes

#### Layouts
- One vertical linear layout, with horizontal linear layouts (2-3 image buttons for ciphers)
- each horizontal layout: wrap_content for vertical to not push away to other horizontals
- use match_parent for horizontal, to fully cover the screen
- the vertical layout: width with match parent to cover full screen
  height to **0dp**, which means "match_constraints"
- Rounded corners: https://stackoverflow.com/questions/21633637/rounded-corners-android-image-buttons
-

#### Other Notes

To center text fields inside a **constraint layout**, you can use:

'''
app:layout_constraintLeft_toLeftOf="parent"
app:layout_constraintRight_toRightOf="parent"
app:layout_constraintTop_toTopOf="parent"
app:layout_constraintBottom_toBottomOf="parent"
'''

To center in vertical_linear_layout: android:layout_gravity: "center"

src: https://stackoverflow.com/questions/43143468/how-to-center-the-elements-in-constraintlayout

 
