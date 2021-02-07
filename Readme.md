Hi!
This project is designed to help you learn how to use 2D arrays.  In addition, you will learn a lot about Color class.

The only files you need to edit are the PictureTester class and the Picture class.

The PictureTester tasks are to complete the following:

testKeepOnlyBlue();
keeps only the blue part of each color, setting the other two to zero

testKeepOnlyRed();
keeps only the red part of each color, setting the other two to zero

testKeepOnlyGreen();
keeps only the green part of each color, setting the other two to zero

testNegate();
creates a negative of the picture.  This basically "flips" the color by replacing
each color attribute with its complement

testGrayscale();
creates a grayscale of the image. It would be interesting to try different approaches, but the mean approach is the easiest

testEdgeDetection();
This requires you to change the Picture class.  The picture class has the method, and you need to write it there.  This could
be done like all the others, but I wanted you to edit both files

testFixUnderwater();
For this task, you have been given a picture taken underwater and you need to edit the pictures to make them look like they would
if the picture were taken without water (low tide)

testChromakey();
Green screen technology.  This superimposes one pic onto another ignoring the pixels that are close (distance-wise) to green.

testEncodeAndDecode(); 
Very cool.  This takes an image that is to hold the secret message.  That image is altered slightly (all of the pixels' red components are made to be even).  
Then the message is superimposed onto the image adding one to every corresponding pixels' red component.  Then the image can be sent to someone else
and they can look for all the pixels that have an odd red component.

testGetCountRedOverValue(250);
gets the number of pixels which have a red component above 250

testSetRedToHalfValueInTopHalf();
cuts all the color components red values to half of what they were, in the top half of the image

testClearBlueOverValue(200);
sets the blue component of a pixel to zero if it is above 200

testGetAverageForColumn(pic,col);
This method calculates the average color of the specified pic and the given column

in the picture class, complete the method
edgeDetection(int edgeDist)