
import java.awt.Graphics2D;

import java.awt.Image;

import java.awt.image.BufferedImage;

import java.io.ByteArrayOutputStream;

import java.io.File;
import java.io.FileInputStream;

import java.io.FileNotFoundException;

import java.io.IOException;


import javax.imageio.ImageIO;





public class Stegnography {

 
   private BufferedImage image=null,new_image=null;
	
private int pixels[][];

	private char Data[];

	private int Width,Height;

	private int Binary[];
	
private int Length=0;

	
	public Stegnography()

	{
		
File fileName=new File("C:\\Users\\Umer\\Desktop\\project.png");

		
		try
		
{
			
new_image= ImageIO.read(fileName);

			
			
image=new BufferedImage(new_image.getWidth(), new_image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		
	
			 Graphics2D graphics   = image.createGraphics();
		
	 graphics.drawRenderedImage(new_image, null);

			 
			 graphics.dispose();


		}
		
catch (IOException e)

		{

			e.printStackTrace();

		}
		
		pixels= new int[image.getWidth()][image.getHeight()];

		
		Binary= new int[8];

		
		Width=image.getWidth();
	
	Height=image.getHeight();
		

		for(int i=0;i<Width;i++)
		
{

			for(int j=0;j<Height;j++)

			
{
	
			pixels[i][j]=image.getRGB(i, j);

			
}
	
	}
	
		
String name= "adsf asdfasd fasdf asdasfa a dfasdfas fas fasdf asdf asdf sadf adsf asdf asd fasdf sadfadsfadsf adsf adsf ads fasdf asdf adsf as dfasfasd fadsf adsf asdf as" 
+
				"df as fasdf asdf asdf asd fas fasf addsf asf adsf asfa dsf dasf as fad fsadf adsf adsf sadf adsf adsf af asddf as dfds" +
	
			"asdfas fasf as fasf asdf sa fasf adfafdsfasdfasf";
	
	Length=name.length();
		

		Data=name.toCharArray();
	
	

		
		
		WriteLSB();

		
		try
		
{
			
ImageIO.write(image, "png", fileName);

		}
		catch (IOException e) 

		{

			
e.printStackTrace();
	
	}
		
		
System.out.println("Encoding Completed");
	
}
	
	private void WriteLSB()

	{		

		int temp,tempText,x=0,p=0,i,j=0;

		Binary((int) Data[x]);
		
x++;
	
	
		
for(i=0;(i<Width && x<Data.length);i++)
		
{

			for(j=0;( j<Height && x<Data.length);j++)

			{

				int change=0;	
				
				
for(int k=0;k<4;k++)
				{
					if(k==0)
					{
						change=1;
					}
					else
						if(k==1)
						{
							change=256;
						}
						else
							if(k==2)
							{
								change=65536;
							}
							else
								if(k==3)
								{
									change = 16777216;
								}
				
					if(Binary[p]==0)
					{
						pixels[i][j]= pixels[i][j] & ~change; // ~1 ki all bits 1 hoti ha except LSB
					}
					else
						if(Binary[p]==1)
						{
							pixels[i][j]= pixels[i][j] | change; // only LSB ko 1 krna ha
						}
					p++;
					
					if(p==8)
					{
						p=0;
						Binary((int) Data[x]);
						x++;
					}
				}
				
			
			
			}
		}

		
		for(i=0;i<Width;i++)
		{
			for(int h=0;h<Height;h++)
			{
				image.setRGB(i, h,pixels[i][h]);
			}
		}
		
	}
	
	private void Binary(int temp)
	{
		Binary=null;
		Binary= new int[8];
		
		for(int i=7;i>=0;i--)// cuz character is of 1 byte
		{
			Binary[i]=temp%2;
			
			System.out.print(Binary[i]);
			temp/=2;
		}
		System.out.println();

	}
}



// for decoding


import java.awt.Image;

import java.awt.image.BufferedImage;

import java.io.BufferedWriter;

import java.io.ByteArrayOutputStream;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.FileOutputStream;
import java.io.FileWriter;

import java.io.IOException;


import javax.imageio.ImageIO;



public class RetreiveData
 {

   
 private BufferedImage image=null;

	private int pixels[][];

	private char Data[];
	
private int Width,Height;
	
private String myString="";
	

	public RetreiveData()
	
{
		
try
		{

			image= ImageIO.read(new File("C:\\Users\\Umer\\Desktop\\project.png"));
	
	}
		catch (IOException e)
	
	{
			e.printStackTrace();
	
	}
		
	
		
		
pixels= new int[image.getWidth()][image.getHeight()];
		

		Width=image.getWidth();
		Height=image.getHeight();
		
		System.out.println(Width);
		System.out.println(Height);
		
		for(int i=0;i<Width;i++)
		{
			for(int j=0;j<Height;j++)
			{
				pixels[i][j]=image.getRGB(i, j);
			}
		}
		
		FileWriter fw=null;
		File file=null;
		BufferedWriter bw=null;
		
		try
		{
			file = new File("C:\\Users\\Umer\\Desktop\\Retreive.txt");
			fw = new FileWriter(file);
			
			bw= new BufferedWriter(fw);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		
		ReadLSB();
		
		try 
		{
			bw.write(myString);
			bw.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		System.out.println(myString);
	}
	
	private void ReadLSB()
	{		
		int temp,tempText=0,x=0,p=0;
		
		for(int i=0;i<Width;i++)
		{
			for(int j=0;j<Height;j++)
			{
				temp=pixels[i][j];
				
				int change=0;	
				
				for(int k=0;k<4;k++)
				{
					if(k==0)
					{
						change=1;
					}
					else
						if(k==1)
						{
							change=256;
						}
						else
							if(k==2)
							{
								change=65536;
							}
							else
								if(k==3)
								{
									change = 16777216;
								}
				

					if((pixels[i][j] & change)==1)	
					{
						tempText|=1 ;
					}
					
					tempText<<=1;
					
					p++;
					if(p==8)
					{
						myString += (char) tempText;// convert int to String and storing in String object
						p=0;
						//x++;
						if(tempText!=272)
						System.out.println("Temp is "+tempText);
						tempText=0;

					}
				}
				
				
			
			}
		}
	}
}
