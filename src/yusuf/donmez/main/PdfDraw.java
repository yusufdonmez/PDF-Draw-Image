package yusuf.donmez.main;

import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject; 

public class PdfDraw{

	private static String folderPath = "C:\\Users\\ysf\\OTO\\doc";
	private static String sign = "powered_by.png";
	private static String awb = "awb1.pdf";


	public static void main(String[] args) {
		PdfDraw test = new PdfDraw();
		test.printURL();
	}

    public void printURL() {

		StringBuffer awbPath = new StringBuffer(folderPath);
		awbPath.append(File.separator);
		awbPath.append(awb);

		StringBuffer pngPath = new StringBuffer(folderPath);
		pngPath.append(File.separator);
		pngPath.append(sign);
        
		File awbFile = new File(awbPath.toString());
		File pngFile = new File(pngPath.toString());

		System.out.printf("pdf file length:%s \t name:%s \n ",awbFile.length(), awbFile.getAbsolutePath());
		System.out.printf("pdf file length:%s \t name:%s \n ",pngFile.length(), pngFile.getAbsolutePath());

		try {
			PDDocument pdDoc = PDDocument.load(awbFile);
			
			PDPage page = pdDoc.getPage(0);

			// PDPage page = new PDPage();
			// add page to the document
			// pdDoc.addPage(page);

			float pageW = page.getTrimBox().getWidth();
			float pageH = page.getTrimBox().getHeight();

			System.out.printf("PDF page w:%s h:%s \n",pageW,pageH);

			// PDImageXObject pdfimg = PDImageXObject.createFromFileByExtension( pngFile, pdDoc); 
			PDImageXObject pdfimg = PDImageXObject.createFromFile(pngPath.toString(), pdDoc);

			int imgW = pdfimg.getWidth();
			int imgH = pdfimg.getHeight();
			System.out.printf("image w:%s h:%s \n",imgW,imgH);
			
			imgW *= 0.03;
			imgH *= 0.03;
			System.out.printf("reduced image w:%s h:%s \n",imgW,imgH);

			//BOTTOM-MIDDLE
			float imgPositionY = 1f;
			float imgPositionX = (pageW/2)-(imgW/2);
			System.out.printf("position x:%s y:%s \n",imgPositionX,imgPositionY);


			try(PDPageContentStream cs = new PDPageContentStream(pdDoc, page, AppendMode.PREPEND,true,true)){
				cs.drawImage(pdfimg, imgPositionX, imgPositionY, imgW, imgH);
			  	System.out.println("png added");
			}

			pdDoc.save(awbFile); 
	
			// Closing the Document 
			pdDoc.close(); 
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}