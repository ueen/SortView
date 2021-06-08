# SortView for Android [![](https://jitpack.io/v/ueen/SortView.svg)](https://jitpack.io/#ueen/SortView)
100% Kotlin
### An easy solution to drag sorting based on Recyclerview

![example](https://user-images.githubusercontent.com/5067479/121024028-1ec93980-c7a4-11eb-96e9-e08b3711c46c.gif)

## Implementation

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
	implementation 'com.github.ueen:SortView:0.4.2'
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
*Make sure to checkout the documentation https://github.com/minseunghyun/slush for more information on how to setup the adapter (it's really easy)*

Notice that SortView takes a type (`String`) that is the same as the list the items are in.

`.onSort(list)` is syncing your list but you could also get the exact positions with `.onSort { startPosition, target ->  }`

you can manually set `.sortDirection(SortView.DRAG_HORIZONTAL)` otherwise its infered in `.onSort`, all dragDirs are supported see https://developer.android.com/reference/androidx/recyclerview/widget/ItemTouchHelper.SimpleCallback

`.equalSpacing()` is optional but helps to space out the items equally if they cover less area than SortView (no scrolling)

### SimpleDialog

Or use the simple SortDialog to quickly get a reordered list
```kotlin
SortDialog.show(this, list) { newList -> ... } //optional: title: String, horizontal: Boolean
```

</br></br>
Ok, hope you enjoy, let me know if you encounter any issues :)

### Example
Theres an example App in the app module, check it out!

![example](https://user-images.githubusercontent.com/5067479/121024028-1ec93980-c7a4-11eb-96e9-e08b3711c46c.gif)

Another example, here SortView is nested in a regular vertical RecyclerView

![example2](https://user-images.githubusercontent.com/5067479/121154037-df075e00-c846-11eb-8969-f183a99b501c.gif)

And the simple Dialog

![example3](https://user-images.githubusercontent.com/5067479/121163371-02360b80-c84f-11eb-8151-1826fca93299.gif)

and with `SortDialog.show(this, list, horizontal = true)`

![example4](https://user-images.githubusercontent.com/5067479/121165751-c7cd6e00-c850-11eb-8d93-eb9f793ebf68.gif)

### SortView builds on great librarys
https://github.com/minseunghyun/slush </br>
https://github.com/fondesa/recycler-view-divider

### License
```
Copyright 2021 ueen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
