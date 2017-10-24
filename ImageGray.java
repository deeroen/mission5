
/**
 * Une classe permettant de manipuler des images en niveaux de gris
 * 
 * @author O. Bonaventure
 * @version Octobre 2012
 */
public class ImageGray
{

    public static void main(String[] args) {

        LibrairieImage.show (LibrairieImage.createCross (890));
        System.out.println ("La premiere fenetre presente une photo celebre d'Albert Einstein");
        LibrairieImage.show (LibrairieImage.imageGrayFromFile ("einstein.jpg"));
    }

    /**
     * @pre img!=null && threshold>=0 && img1.length==img2.length
     *      && img1[0].length==img2[0].length
     * @post retourne l'image composee en retirant de l'image img1 l'image img2
     *       Cette soustraction se fait en comparant les luminances des 
     *       pixels (i,j) correspondants des deux images: 
     *       - si la valeur absolue de la difference de luminance 
     *         entre les deux pixels est inferieure ou egale a threshold, alors
     *         le pixel (i,j) de l'image retournee est blanc
     *       - sinon, le pixel (i,j) de l'image retournee a la luminance
     *         du pixel (i,j) de l'image img1
     *       Les images img1 et img2 n'ont pas ete modifiees
     */

    public static int[][] subtract(int[][] img1, int[][] img2, int threshold)
    {

        assert img1 != null && threshold>=0 && 
        img1.length==img2.length && img1[0].length==img2[0].length : "Bad precondition";
        int[][] result = new int [img1.length][img1[0].length];
        for (int i = 0; i< img1.length ; i++){

            for (int j = 0; j< img1[i].length ; j++) {
                int r =  Math.abs(img1[i][j] - img2[i][j]);
                if (r <= threshold){
                    r = 255;
                } else {
                    r = img1[i][j];
                }
                result [i][j] = r;
            }
        }
        return result;
    }

    /**
     * @pre -
     * @post retourne l'image img eclaircie en calculant la luminance
     *       de chaque pixel de l'image eclaire comme valant 255*sqrt(lum/255)
     *       ou lum est la luminance du pixel courant
     *       L'image img n'a pas ete modifiee par l'execution de cette methode
     */

    public static int[][] brighten(int[][] args)
    {       
        if(args==null){
            System.out.println("du pate sur brighten");
            return null; 
        }
        else{
            int[][] tab= new int [args.length][args[0].length];
            for (int i=0;i<args.length;i++){
                for (int z=0;z<args[0].length;z++){
                    tab [i][z]= 255* (int) Math.sqrt(args[i][z]/255);

                }

            }

            return tab;
        }
    }

    /**
     * @pre img1!=null, img2!=null threshold>=0
     * @post retourne vrai si l'image img2 est une sous-image de l'image img1
     *       avec un seuil threshold. img2 est une sous image de img1 si on peut
     *       retrouver dans l'image img1 une zone qui a les memes dimensions
     *       que img2 et dont la difference des luminances des pixels
     *       correspondants dans la zone et l'image img est inferieure ou egale
     *       en valeur absolue a threshold. 
     *       
     *       Par exemple, considerons l'image img1
     *       0 0 0 0 0 0 0
     *       0 9 0 9 0 0 0
     *       0 0 9 0 6 0 9
     *       0 9 9 9 0 9 0 
     *       0 0 0 0 9 9 9
     *       
     *       et la sous-image
     *       8 0 8
     *       0 8 0
     *       9 9 10
     *       
     *       lorsque threshold vaut zero, contains retourne false
     *       par contre, lorsque threshold vaut 1, contains retournera true
     *       puisque l'on retrouve la sous-image une fois a partir de la 
     *       deuxieme ligne et deuxieme colonne dans l'image img1
     *       Les images img1 et img2 n'ont pas ete modifiees 
     *       par l'execution de cette methode
     */
    public static boolean contains(int[][] img1, int[][] img2, int threshold)
    {
        for(int i = 0; i<img1.length - img2.length; i++){
            for(int j = 0; j < img1[i].length - img2[0].length; j++){
                if(isSimilar(img1,img2,threshold,i,j))
                    return true;
            }
        }
        return false;        
    }

    /**
     * @pre: i < img1.length - img2.length && j < img1[i].length - img2[0].length
     * @post:
     * 
     */
    public static boolean isSimilar(int[][] img1, int[][] img2, int threshold, int i, int j){
        for(int k = 0; k < img2.length; k++){
            for(int l = 0; l < img2[k].length; l ++ ){
                if(Math.abs(img1[i+k][j+l] - img2[k][l]) > threshold)
                    return false;
            }
        }
        return true;
    }

    /**
     * @pre newWidth>0 newHeight>0
     * @post retourne une nouvelle image de dimension newWidth * newHeigth
     *       Chaque pixel de la nouvelle image a comme luminance la luminance
     *       du pixel se trouvant dans le rapport des dimensions de l'image
     *       img
     *       
     *       A titre d'exemple, considerons l'image
     *       0 0 0 0 0 0 0
     *       0 9 0 5 0 0 0
     *       0 0 8 0 6 0 3
     *       0 9 9 5 0 2 0 
     *       0 0 0 0 9 2 3
     *       
     *       Si la nouvelle hauteur est de 10 (rapport *2) et la 
     *       nouvelle largeur de 4 (rapport *4/7) ,
     *       l'image redimensionnee sera
     *       0 0 0 0
     *       0 0 0 0
     *       0 9 5 0
     *       0 9 5 0
     *       0 0 0 0
     *       0 0 0 0
     *       0 9 5 2
     *       0 9 5 2
     *       0 0 0 2
     *       0 0 0 2 
     *       L'image img n'a pas ete modifiee par l'execution de cette methode
     *       
     */

    public static int[][] rescale(int[][] img, int newHeight, int newWidth)
    {
        float ratioH = (float)newHeight / (float) img[0].length;
        float ratioW = (float)newWidth / (float) img.length;
        int [][] result = new int [newWidth][newHeight];
        for(int i = 0; i < result.length; i++){
            for(int j = 0; j < result[i].length; j++){
                int indI = Math.min((int)Math.round((float)i / ratioW), img.length - 1);
                int indJ = Math.min((int)Math.round((float)j / ratioH), img[0].length - 1);

                result[i][j] = (int) img[indI][indJ];
            }
        }
        return result;
    }
}
