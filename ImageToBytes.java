import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.awt.Color;
public class ImageToBytes
{
	public static final int RGBA_BitDepth = 4;
	public ByteBuffer returnBytes(BufferedImage image)
	{
		byte[] imageBytes = new byte[image.getWidth()*image.getHeight()*RGBA_BitDepth];
		//creates an array of bytes that holds all possible bytes of the image. Since each RGBA value is 0-255, it means it has 1 byte of data (2^8 = 256), which means each pixel has 1 byte of data, which means widthOfImage*heightOfImage*bitDepth = total bytes an image has!
		for(int i = 0; i < image.getWidth(); i++)
		{
			for(int j = 0; j < image.getHeight(); j++)
			{
				int currentPixel = image.getRGB(i, j);//should check .getRGB()
				//*Note: From what I've read on the internet, a pixel's byte representation for RGBA is in the AAAAAAAARRRRRRRRGGGGGGGGGBBBBBBBB format (8 bits/component)
				for(int k = 1; k < 4; k++)
				{
					imageBytes[(i*image.getWidth()*RGBA_BitDepth)+j] = (byte)((currentPixel >> 8*(3-k))&0xFF);//RGBA
					//System.out.print(" " + i + " "  + j + " "+ k + ": " + (byte)((currentPixel >> 8*(3-k))&0xFF));// -^
					//System.out.print(" " + k + ": " + (byte)(((~currentPixel << 8*k) >> 8*(3-k)) ^ 255)); //-v
					//imageBytes[(i*image.getWidth()*RGBA_BitDepth)+j] = (byte)(((~currentPixel << 8*k) >> 8*(3-k)) ^ 255);//RGBA
					//Explaining the index of imageBytes: (i = current row) * (image.getWidth() = total # of j's would've iterated thru) * (RGBA_BitDepth = since we're translating R,G,B, and A one by one, that means 1 full pixel iteration = 4 bytes) + (j = current column))
					
					//Explaining k usage in shifts - R = << 1, G = << 2, B = << 3, R = >> 2, G = >> 1, B = >> 0, therefore k and (3-k)
						//*We multiply by 8 because 8 bits
					
					//Bit Puzzle - extracting R, G, B, and A. Let's explain using G as an example.
						//Original = 01010101 01010101 01010101 01010101
						//Inverse = 10101010 10101010 10101010 10101010 - we want only G for instance, the 3rd byte. So we get rid of the bits on the left by shifting <<. Shifting >> turns them to 111's, which since we want to treat them as 0's, we inverse it first.
						//<< then >> = 11111111 11111111 11111111 10101010
						//^ w/ 255 = 111111111 11111111 11111111 1111111
						//Result = 00000000 0000000 00000000 01010101
				}
					imageBytes[(i*image.getWidth()*RGBA_BitDepth)+j] = (byte)((currentPixel >> 24) & 255); //Alpha
					//imageBytes[(i*image.getWidth()*RGBA_BitDepth)+j] = (byte)(((~currentPixel) >> 24) ^ 255); //Alpha
					//System.out.println("Alpha: " + (byte)((currentPixel >> 24) & 0xFF));
					//System.out.println(" Alpha: " + (byte)(((~currentPixel) >> 24) ^ 0xFF));
			}
		}
		return ByteBuffer.wrap(imageBytes);//ByteBuffer is by definition an array of bytes that hold pixel data.	
	}
}
/*
Source code from internet. Used as reference
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            int pixel = image.getRGB(j, i);
            for (int k = 0; k < 3; k++) // red, green, blue
                imageBytes[(i*16+j)*4 + k] = (byte)(((pixel>>(2-k)*8))&255);
            imageBytes[(i*16+j)*4 + 3] = (byte)(((pixel>>(3)*8))&255); // alpha
        }
    }

 int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

       for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                buffer.put((byte) (pixel & 0xFF));               // Blue component
                buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
            }
        }
        buffer.flip(); //FOR THE LOVE OF GOD DO NOT FORGET THIS
*/