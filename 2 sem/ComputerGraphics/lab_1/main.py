from typing import List
from tkinter import *


class Point:
    def __init__(self, x, y):
        self.x = x
        self.y = y


class SmoothConvex(Frame):
    diam = 5
    point_color = "red"

    def __init__(self, parent):
        self.vertexes: List[Point] = []

        Frame.__init__(self, parent)
        self.parent = parent

        self.pack(fill=BOTH, expand=1)

        self.columnconfigure(6, weight=1)
        self.rowconfigure(2, weight=1)

        self.canvas = Canvas(self, bg="white")
        self.canvas.grid(row=2, column=0, columnspan=7, padx=5, pady=5, sticky=N+S+W+E)

        self.canvas.bind("<Button-1>", self.on_touch_left)
        self.canvas.bind("<Button-2>", self.on_touch_right)

    def on_touch_left(self, event):
        self.vertexes.append(Point(event.x, event.y))
        self.update_view()

    def on_touch_right(self, event):
        self.update_view()
        z = Point(event.x, event.y)
        if check_if_inside(z, self.vertexes, self.canvas):
            self.canvas.create_text(200, 50, text="Inside", fill="green", font="Helvetica 30 bold")
            print("Inside")
        else:
            print("Outside")
            self.canvas.create_text(200, 50, text="Outside", fill="red", font="Helvetica 30 bold")

    def update_view(self):
        self.canvas.delete("all")
        if len(self.vertexes) == 0:
            return
        for p in self.vertexes:
            self.canvas.create_oval(p.x - self.diam / 2, p.y - self.diam / 2, p.x + self.diam / 2,
                                    p.y + self.diam / 2, fill=self.point_color,
                                    width=3, outline=self.point_color)
        draw_polygon(self.vertexes, self.canvas)

    def on_clear(self):
        self.vertexes = []
        self.update_view()


def draw_polygon(vertexes: List[Point], canvas: Canvas):
    if len(vertexes) < 2:
        return
    line = []
    for p in vertexes:
        line.append(p.x)
        line.append(p.y)
    p = vertexes[0]
    line.append(p.x)
    line.append(p.y)
    canvas.create_line(line, width=2, fill='blue')


def check_if_inside(z: Point, vertexes: List[Point], canvas: Canvas) -> bool:
    if len(vertexes) < 3:
        return False
    canvas.create_line([z.x, z.y, z.x + 800, z.y], width=3, fill="orange", activefill='orange')
    a = vertexes[0]
    inside = is_inside(z, vertexes[len(vertexes) - 1], a)
    for i in range(0, len(vertexes[1:])):
        b = vertexes[1:][i]
        if is_inside(z, a, b):
            inside = not inside
        a = b
    return inside


def is_inside(p: Point, a: Point, b: Point) -> bool:
    return (a.y >= p.y) != (b.y >= p.y) and p.x <= (b.x - a.x) * (p.y - a.y) / (b.y - a.y) + a.x


def main():
    root = Tk()
    root.geometry("800x600")
    SmoothConvex(root)
    root.mainloop()


main()
