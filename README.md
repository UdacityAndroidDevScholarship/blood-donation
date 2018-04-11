> **Blood Donation App**

Hello Everyone,

This application is a ongoing collaborative project made by the `Google Udacity Android Developer Scholarship Recepients`.

---

## how the app will work?
 * First of all, the one(Voluntary Donor) firstly makes an account on our app with general information(e.g. name, age) + his Blood Group + his live location(As per his permission) or City name.
 * On the second side, the one who needs blood,will create a request for blood unit with his Location and all person near him(Our Voluntary Donors) will receive notification.(For example, `XYZ` needs blood at `XYZ hospital` or `123.456.789` location)
 
   **OR**
   
   We can show him all the blood donors with that particular blood group with the live location on the map. (Consider an example when we search for cab on `Ola` or `Uber` app

---

## Benefits of the Project 

1) Reduce the wastage of blood. Usually Blood banks store more blood so that they full fill every requirement because the demand for blood is undefined.
2) Sometimes people don't find blood at the correct time.This app can help there.

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



## Understanding MVP arch.

The project has a package name `base`.
It contains mainly 6 files

```
base
  - BaseActivity
  - BaseFragment
  - BaseMvpPresenter
  - BasePresenter
  - BaseView
  - PresenterFactory
```

***BaseView***
```
public interface BaseView {
.........
}
```
An base interface used to communicate with view

***BaseMvpPresenter***
```
public interface BaseMvpPresenter<V extends BaseView> {
.........
}
```
An interface used to assign view to the presenter

***BasePresenter***
```
public class BasePresenter<V extends BaseView> implements BaseMvpPresenter<V> {

.........
}
```
A class used to connect presenter to the view

***BaseActivity***
```
public abstract class BaseActivity<T extends BaseMvpPresenter, K extends ViewDataBinding> extends AppCompatActivity implements BaseView {

.........
}
```
Our base class for the activity. It has 2 generics which connect the Databinder and presenter to this activity.

***BaseFragment***
```
public abstract class BaseFragment<T extends BaseMvpPresenter, K extends ViewDataBinding> extends Fragment implements BaseView {
.........
}
```
Our base class for the fragment. It has 2 generics which connect the Databinder and presenter to this activity.

***PresenterFactory***
```
public class PresenterFactory{
.........
}
```
This class is used to generate presenter models.



## Reason for MVP arch :
1. It creates the simplicity to write the code for Unit Testing ( Unit Testing encourages developers to modify the source code).
2. We will be able to keep track of the logic, which is inside the class.
3. Easy to divide the work.


## Accessing the System
Whenever we create a new activity. First create a package with your activity name .Remeber to create 3 main files.
(For this context I am assuming the activity name as foo)
1. FooActivity ( extend BaseActivity and should implement FooContract.View. Has view Methods)
2. FooContract ( Has the presenter and view definitions)
3. FooPresenter ( Has the presenter methods. Should implement FooContract.Presenter)

Example :
After creating you package it should look something like this.

```
foo
  - FooActivity
  - FooContract
  - FooPresenter
```

Then in your layout <activity_foo> add the lines for data binding.
```
<xml........>
<layout>
   <data> </data>
   
   /// Your layout code 
</layout>
```
Remeber to put the `layout` only at the begining of the file excatly beneath the `<xml...>` definition or else the complier sometimes might not recognise the layout leading to not generating the classes.

Rebuild the project once to generate the binding class . **IMPORTANT**

Next define your classes as follows 

1. FooContract

```
interface FooContract{
    public interface Presenter extends BaseMvpPresenter<View>{
        void checkView();
    }
    public interface  View extends BaseView{
        void checkData();
    }
}
```
2. FooActivity

```
public class FooActivity extends BaseActivity<FooActivity.Presenter, ActivityFooBining> implements HomeContract.View {
...
}

```
Remember that the `ActivityFooBining` wount generate until you rebuild after defining the layout.

3. FooPresenter
```
public class FooPresenter extends BasePresenter<FooContract.View> implements FooContract.Presenter {
...
}
```

Now we can access the view methods in FooPresenter and presenter methods from FooActivity.


***Side note:*** Always remember keep all your logic in the presenter and make your activity code look as dumb as possible. And everything related to view system must never be accessed in the presenter. That's it HAPPY CODING
