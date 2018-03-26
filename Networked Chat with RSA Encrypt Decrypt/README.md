# University of Illinois (Chicago)

# Networked Chat with RSA Encryption/Decryption in Java

## Objective
This project is a set of GUI based programs in Java Swing that will allow multiple people to connect together and send encrypted messages to the chat room. The Java Socket and ServerSocket classes are used for the networking implementation. The RSA encryption/decryption code was designed and written by the team.

## Functionality
One user chooses to become the server. An IP address and port number are displayed to the server and should be given to any user who wants to join the chat room. When sending messages, the message is encrypted before being sent, it continues to be encrypted when it reaches the server (evident in the example), it reaches all of the clients who will then run the decryption algorithm.

###### Encryption/Decryption
Since normal messages can be “stolen” from the packets during transmission, we used the RSA algorithm to provide Encryption/Decryption of the message(s). 

Example Execution: https://github.com/akhan227/School-Projects/blob/master/EncryptedNetworkChatRSA-Java/connect.PNG

###### Note
This program was completed between a team of 3 individuals, including myself.
Ahmed Khan
Ryan Moran
Edgar Martinez

This design doc thoroughly explains the program we created: https://github.com/akhan227/School-Projects/blob/master/EncryptedNetworkChatRSA-Java/proj5designdoc.pdf