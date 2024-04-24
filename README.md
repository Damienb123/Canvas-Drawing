A simple user interactive drawing application

  Canvas Drawing: The MyView class extends View and provides a custom drawing canvas. It initializes various shapes such as rectangles and circles and draws them on the canvas using the Canvas API.

  User Interaction: The app allows users to interact with the canvas by tapping on it. When the user taps on the canvas, the onTouchEvent method is triggered, and the app responds by either changing the color of a rectangle or adding a new point to the canvas.

  Drawing Shapes: The canvas draws various shapes such as rectangles and circles with different colors and styles. For example, there's a rectangle with text inside it, a circle that can be moved around by tapping, and lines connecting points on the canvas.

  Dynamic Rendering: The app dynamically updates the canvas based on user input. Whenever the user interacts with the canvas, such as tapping on it, the onDraw method is called to redraw the canvas with the updated shapes and positions.
