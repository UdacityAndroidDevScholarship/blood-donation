![blood donation app](https://user-images.githubusercontent.com/25679263/40031419-42021df4-580d-11e8-941f-3f3b80458998.png)


---
## Check v1.0
[![Blood_Donation_Kamal](https://user-images.githubusercontent.com/25679263/40282658-4ed4c752-5c90-11e8-94ce-c7db6c1f91ac.jpg)](https://www.youtube.com/watch?v=HXrusHKrLQE "Blood Donation App V1.0 - Udacity Google India Scholars")

---
## How the app will work?
 * This app keeps a list of voluntary donors.
 * A Voluntary Donor has to make an account on our app with some general information like:
    - Name
    - Age
    - Blood Group
    - City name [Can also provide access to his live location].
    
 * On the other side, the one who needs blood, will create a request for blood unit with his blood group and location.

### On Creating a Blood Request 
All persons near him i.e. *our voluntary donors* will receive notification for such request. (e.g. `Person A` needs blood at `XYZ hospital` or `123.456.789` location)
 
   **OR**
   
   The requester is shown a map of all the blood donors with the requested blood group with their live location near him. (Consider an example when we search for cab on `Ola` or `Uber` app)

---

## Benefits of the Project 

1) Connecting Blood Donors and Recipients.
   - Sometimes people don't find blood at the correct time. This app can help there by connecting donors and recipients.
2) Reduce the wastage of blood. 
   - Usually Blood banks store more blood so that they fulfil every requirement because the demand for blood is undefined.

---


## Use the project with your own Firebase instance and Google Maps API key

* Create a new project in the [Firebase console][1].
* Click Add Firebase to your Android app
   * use the `applicationId` value specified in the `app/build.gradle` file of the app as the Android package name
   * insert SHA-1 fingerprint of your debug certificate
* Download the generated `google-services.json` file, and copy it to the `app/` directory of your project.


* Get you own google map API key. See the [quick guide to getting an API key][2].
* Find `.gradle` folder in your home directory, create a file named `gradle.properties` (if not present).

    Usually it can be found at:
    
        Windows: C:\Users\<Your Username>\.gradle
        Mac: /Users/<Your Username>/.gradle
        Linux: /home/<Your Username>/.gradle
    
        Inside it there would be a file named gradle.properties (just create it if there isn’t any).

* Open the `gradle.properties` file and paste your API key into the value of the `GOOGLE_MAPS_API_KEY` property, like this

    `GOOGLE_MAPS_API_KEY=PASTE-YOUR-API-KEY-HERE`

* Now you should be able to successfully sync the project.
---

## Cloud Functions for push notifications

* Cloud functions will be used to send notifications to the donors when new blood requests as that of the donor is created.
* The cloud functions code is being written at https://github.com/groverankush/blood_donation_functions Feel free to contribute to it.
* To setup and test cloud functions on your local system, visit https://firebase.google.com/docs/functions/

## Contributing

We welcome your contributions to this project. There are various ways to contribute:

**Reporting issues**

Help improve the project by reporting issues that you find by filing a new issue at the [Blood Donation App issue tracker][0].

**Features suggestions**

You can also add feature suggestions by filing a new issue at the [Blood Donation App issue tracker][0].

Pull requests are welcome for minor bug fixes or for a feature you are working on.

**Suggestion are Welcome**

---

[0]: https://github.com/UdacityAndroidDevScholarship/blood-donation/issues
[1]: https://console.firebase.google.com
[2]: https://developers.google.com/maps/documentation/android-api/signup
