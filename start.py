from PyQt5 import QtWidgets
from PyQt5.QtWidgets import *
from PyQt5 import QtCore
from PyQt5 import QtGui
import sys

class EvacGui(QMainWindow):
    def __init__(self):
        super(EvacGui,self).__init__()
        self.setWindowIcon(QtGui.QIcon('icon-circle.png'))
        self.setGeometry(200,200,300,300)
        self.setWindowTitle("EvacSim")
        self.initUI()

    def initUI(self):
        self.label = QtWidgets.QLabel(self)
        self.label.setText("placeholder")
        self.label.move(50,50)

        self.b1 = QtWidgets.QPushButton(self)
        self.b1.setText("Click me")
        self.b1.clicked.connect(self.clicked)

    def clicked(self):
        self.label.setText("button pressed")
        self.update()

    def update(self):
        self.label.adjustSize()

def window():
    app = QApplication(sys.argv)
    win = EvacGui()
    win.show()
    sys.exit(app.exec())
window()