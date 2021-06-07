# SortView for Android

### An easy solution to drag sorting based on Recyclerview
100% Kotlin

### Example
Theres an example App in the app module, check it out!

![example](https://user-images.githubusercontent.com/5067479/121024028-1ec93980-c7a4-11eb-96e9-e08b3711c46c.gif)

## Implemention

Step 1. Add the JitPack repository to your build file 
```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Step 2. Add the dependency
```groovy
	dependencies {
	        implementation 'com.github.ueen:SortView:0.1'
	}
```


## Usage

SortView is basically a Recyclerview, so just add it to your xml as you would do normally
```xml
<de.ueen.sortview.SortView
        android:id="@+id/sortView"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:padding="16dp"
        tools:listitem="@layout/string_item" />
```
*No worries padding is accounted for, vertical or horizontal orientation are supported*

Also create a xml with how each item should look (here `R.layout.string_item`)

Next in your Activity/Fragment setup SortView like this
```kotlin
        val list = listOf<String>("hi","whats","going","on")

        val sortView = findViewById<SortView<String>>(R.id.sortView)
            .setupAdapter { adapter ->
                adapter.setItemLayout(R.layout.string_item)
                adapter.setLayoutManager(
                    LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false))
                adapter.onBind { view, s ->
                    view.findViewById<TextView>(R.id.textView).text = s }
                adapter.setItems(list)
            }
            .onSort(list) //infers direction
            .equalSpacing() //optional: showFirstDivider: Boolean, showLastDivider: Boolean
```
Make sure to checkout //documentation https://github.com/minseunghyun/slush for more information on how to setup the adapter (it's really easy)

you can set `.sortDirection(SortView.DRAG_HORIZONTAL)` manually after `.onSort` otherwise it is infered there. All dragDirs are supported </br>
see https://developer.android.com/reference/androidx/recyclerview/widget/ItemTouchHelper.SimpleCallback

`.onSort` is syncing your list but you could also get the exact position with `.onSort { startPosition, target ->  }`

`.equalSpacing()` is optional but helps to space out the items equally if they cover less area than SortView (no scrolling)

</br></br>
Ok, hope you enjoy, let me know if you encounter any issues :)

### SortView builds on great librarys
https://github.com/minseunghyun/slush </br>
https://github.com/fondesa/recycler-view-divider

