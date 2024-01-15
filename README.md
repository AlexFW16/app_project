# 0xCrypt
**Try out how the basic ciphers work and get some intuition**

### coursework specifications
- [X] Comprise of good quality UI with at least two fragments separating all the components.
- [X] Use the activity life cycle states through its various methods (such as onStart, onPause, onDestroy,
etc.).
- [X] Utilise at least one: device location/ Google Maps/ Media player/ Camera/ microphone/ any native
android apps, service, sensor, or sensor API.
- [X] Use persistence data storage (e.g. SQLite, ROOM/ Firebase/Firestore, etc.).
- [X] Use MVC or similar (java-based frameworks such as MVP, MVVM, etc.) framework.


### TODO
- [X] Template activity for standard ciphers, must be adapted depending on which cipher is used at runtime
- [X] Make input fields, etc. look nice
- [X] Debug Ciphers (especially Vigenere)
- [X] Update settings page
- [X] Put strings from update password xml into strings.xml
- [X] Add another fragment (bottom) that is used to display more detailed information (xor: binary code, ...)
- [X] Should work? SpeechRecognizer is not properly closed
- [X] Weird that the fragments change their id's, probably bc they are the same and get overriden on change or smth? 
  -> Just use the same id for the same buttons etc.
- [X] Error messages for wrong input in encryption
- [X] Change binary mode to switch instead of button
- [X] Debug XOR cipher
- [X] Descriptions/how to use for all ciphers
- [X] Maybe issues when restoring data with isBinary in XOR?
- [ ] (Add key/password generator)

- [ ] Email changing not working; Would be a lot of effort to do it properly
- [ ] Add switch button (output text and msg)
- [ ] (We could also add the option to save inputs and have a history -> Lot of work, for now only the last used snapshot)

- ~~Make 2 colour schemes, one for decrypting and one for encrypting~~

#### Firebase Realtime Database
- We use the Firebase Authentification Service to provide user accounts
- The last snapshot of a user is stored on the firebase realtime database (a snapshot contains key, message and output of all ciphers)
- When the user logs in again, his/her snapshot gets restored and he/she can continue working where he/she left

### How is MVC implemented?
- The encryption/decryption process is fully separated from the frontend
- The cipher fragments simply call encrypt/decrypt with the corresponding cipher and input
- Furthermore, there is a simple DataModel created, that allows to easily store snapshots that can be saved.
  A snapshot consists of all the users in/output which is stored in the Prefs File locally and online if the
  user logs out.

### Case Study / Problem
- Help people learn/understand how cryptography works in an intuitive and easy way. Lots of ways to expand:
  - Add new ciphers
  - Add Product ciphers (combination of transposition and substitution ciphers)
  - Add functionality to create/save ciphers; relevant for prodcut ciphers aswell
  - Add feedbackShiftRegister
  - $\dots$

