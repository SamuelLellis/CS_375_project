import os
from pathlib import Path

import matplotlib.pyplot as plt
import numpy as np

FOLDER = ""


# Test function with coefficients as parameters
def test(x, a, b):
    return a * np.sin(b * x)


def smooth(x, y, amount):
    r = 10
    iterations = 0
    for _ in range(amount):
        for i in range(len(x)):
            s = 0
            c = 0
            for j in range(-r, r + 1):
                if 0 <= i + j < len(x):
                    s += y[i + j]
                    c += 1
            y[i] = s / c

        poly = np.polyfit(x[: len(x) - r], y[: len(y) - r], deg=4)
        y[len(x) - r:] = np.polyval(poly, x)[len(x) - r:]

    # for i in range(1, amount):
    #     x_new = np.linspace(x[0], x[-1], 20 * amount)
    #     spl = interpolate.make_interp_spline(x, y)
    #     y_new = spl(x_new)
    #     x[:] = x_new
    #     y[:] = y_new


def plot(title, smoothing, x, y):
    # for i in range(1, smoothing + 1):
    #     print("SMOOTH")
    #     smooth(x, y, i)
    smooth(x, y, smoothing)

    plt.ylabel('Calculation time (ms)')
    plt.xlabel('Number of items')

    line, = plt.plot(x, y, linewidth=1)
    line.set_label(title)


def run(data, title: str, smoothing):
    plot(title, smoothing, data[title][0], data[title][1])


def main():
    data = {}
    for p in Path(FOLDER).iterdir():
        if p.is_dir():
            continue
        with p.open() as f:
            lines = f.read().splitlines()[1:]

        x = []
        y = []
        for line in lines:
            if line.startswith("#") or not line.strip():
                continue
            parts = line.split(",")
            x.append(int(parts[0]))
            y.append(float(parts[1]) / 1e6)
        parts = p.name.split("-")
        name = " ".join([x.capitalize() for x in parts[0].split("_")])
        fraction = parts[1][0:parts[1].rindex('.')]
        data[name + f" \u03A9={fraction}"] = x, y

    show(data)
    # show(data, "Brute Force")
    # show(data, "Best First")
    # show(data, "Backtracking")


def show(data):
    omega = 0.5
    real_title = f"Best First \u03A9={omega}"
    run(data, real_title, 0)
    plt.legend()
    plt.title(f"Best First No Smoothing \u03A9={omega}")
    # plt.savefig("images/" + real_title + '.png', dpi=1000)
    plt.savefig(f"images/BF-{omega}.png", dpi=1000)
    plt.show()
    os.makedirs("images", exist_ok=True)
    # run(data, "Best First Ω=0.01", 10, True)


# def show(data):
#     omega = 0.99
#     for method in ["Best First", "Backtracking", "Brute Force"]:
#         real_title = method + f" \u03A9={omega}"
#         run(data, real_title, 3)
#     plt.legend()
#     plt.title(f"Comparison \u03A9={omega}")
#     # plt.savefig("images/" + real_title + '.png', dpi=1000)
#     plt.savefig(f"images/Comparison-{omega}.png", dpi=1000)
#     plt.show()
#     os.makedirs("images", exist_ok=True)
#     # run(data, "Best First Ω=0.01", 10, True)


# def show(data, title):
#     for i in [0.01, 0.5, 0.99]:
#         # for i in [0.99]:
#         real_title = title + f" \u03A9={i}"
#         run(data, real_title, 2)
#     plt.legend()
#     plt.title(title)
#     plt.savefig("images/" + real_title + '.png', dpi=1000)
#     plt.show()
#     os.makedirs("images", exist_ok=True)
#     # plt.savefig("images/" + title + '.png', dpi=1000)
#     # run(data, "Best First Ω=0.01", 10, True)


if __name__ == '__main__':
    main()
