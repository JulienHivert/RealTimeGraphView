# RealTimeGraphView
Android code for draw in real time graphs

Android code example showing how to draw one or two graphs in Real time.

In your root build.gradle 

```gradle
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
		}
	}
```

Add these lines inside your build.gradle (Module: app)
```gradle
implementation 'com.github.JulienHivert:RealTimeGraphView:1.0.1'
```
In Your XML 
```xml
<com.jhivert.graphviewlib.GraphView
        android:id="@+id/graphView"
        android:layout_width="match_parent"
        android:layout_height="128dp"
        app:graphColor_1="@color/colorPrimary"
        app:graphColor_2="@color/colorAccent"
        app:stroke="7.0" />

```
In Your Java / Kotlin Class
```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        graphView.drawFirstGraph(DataPoint(progress, value))
        graphView.drawSecondGraph(DataPoint(prgress, value))
    }
}
```

I simulate the "real time" by using a thread who sleep each 100ms
The graph is drawn from left to right from the bottom left corner.
In the case of a graphic mutli, the drawings of the 2 graphics adapt automatically according to the Y that you sent so that the 2 graphics keep the same scale

For example if the Y of your graph 2 is higher than that of your graph, the drawing of graph 1 will apply a little in order to adapt to the new scale

# Video

https://imgur.com/gallery/xy2wsnp

# Improvements

If you have any suggrstions, idea for improvement the lib do not hesitate to fork the lib or make me pull request. If you have any do not hesitate to ask me I will enjoy to help you and work with you 
