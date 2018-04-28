# **Blood Donation App**

Hello Everyone,

This application is an ongoing collaborative project made by the `Google Udacity Android Developer Scholarship Recipients`.

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

## Contributing

We welcome your contributions to this project. There are various ways to contribute:

**Reporting issues**

Help improve the project by reporting issues that you find by filing a new issue at the [Blood Donation App issue tracker][0].

**Features suggestions**

You can also add feature suggestions by filing a new issue at the [Blood Donation App issue tracker][0].

Pull requests are welcome for minor bug fixes or for a feature you are working on.

---

[0]: https://github.com/UdacityAndroidDevScholarship/blood-donation/issues
[1]: https://console.firebase.google.com
[2]: https://developers.google.com/maps/documentation/android-api/signup
