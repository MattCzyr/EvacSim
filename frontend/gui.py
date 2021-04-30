from PyQt5 import QtCore, QtGui, QtWidgets
from PyQt5.QtWidgets import QApplication, QWidget, QInputDialog, QLineEdit, QFileDialog
import os

class Ui_MainWindow(object):

    # This is the main window for the UI, meaning this is where all the widgets for
    # the main sim setup screen are instantiated.  Names and other properties can be
    # changed below.

    def setupUi(self, MainWindow):
        MainWindow.setObjectName("MainWindow")
        MainWindow.resize(572, 434)
        MainWindow.setLayoutDirection(QtCore.Qt.LeftToRight)
        self.centralwidget = QtWidgets.QWidget(MainWindow)
        self.centralwidget.setObjectName("centralwidget")
        self.runSimulation = QtWidgets.QPushButton(self.centralwidget, clicked = lambda: self.submit())
        self.runSimulation.setGeometry(QtCore.QRect(450, 390, 111, 31))
        self.runSimulation.setObjectName("runSimulation")
        self.title = QtWidgets.QLabel(self.centralwidget)
        self.title.setGeometry(QtCore.QRect(110, 0, 201, 101))
        font = QtGui.QFont()
        font.setPointSize(38)
        self.title.setFont(font)
        self.title.setObjectName("title")
        self.disasterSelection = QtWidgets.QComboBox(self.centralwidget)
        self.disasterSelection.setGeometry(QtCore.QRect(360, 120, 111, 21))
        self.disasterSelection.setObjectName("disasterSelection")
        self.disasterSelection.addItem("")
        self.disasterSelection.addItem("")
        self.disasterSelection.addItem("")
        self.disasterSelectionLabel = QtWidgets.QLabel(self.centralwidget)
        self.disasterSelectionLabel.setGeometry(QtCore.QRect(10, 110, 341, 41))
        font = QtGui.QFont()
        font.setPointSize(12)
        self.disasterSelectionLabel.setFont(font)
        self.disasterSelectionLabel.setObjectName("disasterSelectionLabel")
        self.edgesLabel = QtWidgets.QLabel(self.centralwidget)
        self.edgesLabel.setGeometry(QtCore.QRect(10, 150, 201, 41))
        font = QtGui.QFont()
        font.setPointSize(12)
        self.edgesLabel.setFont(font)
        self.edgesLabel.setObjectName("edgesLabel")
        self.edgesBrowse = QtWidgets.QPushButton(self.centralwidget)
        self.edgesBrowse.setGeometry(QtCore.QRect(200, 160, 75, 23))
        self.edgesBrowse.setObjectName("edgesBrowse")
        self.nodesBrowse = QtWidgets.QPushButton(self.centralwidget)
        self.nodesBrowse.setGeometry(QtCore.QRect(200, 200, 75, 23))
        self.nodesBrowse.setObjectName("nodesBrowse")
        self.nodesLabel = QtWidgets.QLabel(self.centralwidget)
        self.nodesLabel.setGeometry(QtCore.QRect(10, 190, 201, 41))
        font = QtGui.QFont()
        font.setPointSize(12)
        self.nodesLabel.setFont(font)
        self.nodesLabel.setObjectName("nodesLabel")
        self.modelsBrowse = QtWidgets.QPushButton(self.centralwidget)
        self.modelsBrowse.setGeometry(QtCore.QRect(350, 240, 75, 23))
        self.modelsBrowse.setObjectName("modelsBrowse")
        self.directoryLabel = QtWidgets.QLabel(self.centralwidget)
        self.directoryLabel.setGeometry(QtCore.QRect(10, 230, 331, 41))
        font = QtGui.QFont()
        font.setPointSize(12)
        self.directoryLabel.setFont(font)
        self.directoryLabel.setObjectName("directoryLabel")
        self.icon = QtWidgets.QLabel(self.centralwidget)
        self.icon.setGeometry(QtCore.QRect(10, 10, 81, 81))
        self.icon.setText("")
        self.icon.setPixmap(QtGui.QPixmap("icon-circle.png"))
        self.icon.setScaledContents(True)
        self.icon.setObjectName("icon")
        self.autorunCheck = QtWidgets.QCheckBox(self.centralwidget)
        self.autorunCheck.setGeometry(QtCore.QRect(0, 270, 251, 31))
        self.autorunCheck.setLayoutDirection(QtCore.Qt.RightToLeft)
        self.autorunCheck.setObjectName("autorunCheck")
        MainWindow.setCentralWidget(self.centralwidget)

        self.retranslateUi(MainWindow)
        QtCore.QMetaObject.connectSlotsByName(MainWindow)

    # This is the retranslation code, meaning everytime the main window undergoes
    # a change, the property update values must go here.  Names and other properties 
    # can be changed below as with the main window, but must be tied to a trigger.
    def retranslateUi(self, MainWindow):
        _translate = QtCore.QCoreApplication.translate
        MainWindow.setWindowTitle(_translate("MainWindow", "MainWindow"))
        self.runSimulation.setText(_translate("MainWindow", "Run Simulation"))
        self.title.setText(_translate("MainWindow", "EvacSim"))
        self.disasterSelection.setCurrentText(_translate("MainWindow", "Earthquake"))
        self.disasterSelection.setItemText(0, _translate("MainWindow", "Earthquake"))
        self.disasterSelection.setItemText(1, _translate("MainWindow", "Tsunami"))
        self.disasterSelection.setItemText(2, _translate("MainWindow", "Hurricane"))
        self.disasterSelectionLabel.setText(_translate("MainWindow", "What type of disaster do you want to simulate?"))
        self.edgesLabel.setText(_translate("MainWindow", "Select your edges.csv file"))
        self.edgesBrowse.setText(_translate("MainWindow", "Browse"))
        self.nodesBrowse.setText(_translate("MainWindow", "Browse"))
        self.nodesLabel.setText(_translate("MainWindow", "Select your nodes.csv file"))
        self.modelsBrowse.setText(_translate("MainWindow", "Browse"))
        self.directoryLabel.setText(_translate("MainWindow", "Select the directory where models are located"))
        self.autorunCheck.setText(_translate("MainWindow", "Open Google Earth Pro on sucessful simulation"))
        
        # These are the triggers for our browse buttons.  When clicked,
        # each trigger their respective browse dialog in the functions
        # below.  This can be potentially streamlined.
        self.edgesBrowse.clicked.connect(self.edgeBrowseFile)
        self.nodesBrowse.clicked.connect(self.nodeBrowseFile)
        self.modelsBrowse.clicked.connect(self.modelBrowseFile)

    # Property trigger for submit button
    def submit(self):
        self.runSimulation.setText("submitted")

    #With seperate browse functions, we can change button properties much easier.
    def edgeBrowseFile(self):
        file = self.openDialog()
        self.edgesBrowse.setText(os.path.basename(file))
    def nodeBrowseFile(self):
        file = self.openDialog()
        self.nodesBrowse.setText(os.path.basename(file))
    def modelBrowseFile(self):
        file = self.openDialog()
        self.modelsBrowse.setText(os.path.basename(file))

    # Function to open the file dialog for each respective button
    def openDialog(self):
        filename = QFileDialog.getOpenFileName(filter='Comma Seperated Values (*.csv)')
        path = filename[0]
        print(path)
        return path

if __name__ == "__main__":
    import sys
    app = QtWidgets.QApplication(sys.argv)
    MainWindow = QtWidgets.QMainWindow()
    ui = Ui_MainWindow()
    ui.setupUi(MainWindow)
    MainWindow.show()
    sys.exit(app.exec_())
