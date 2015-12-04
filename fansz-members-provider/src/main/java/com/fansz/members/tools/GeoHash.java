package com.fansz.members.tools;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class GeoHash {

    private static final double R = 6371004.0;//米

    private static int numBits = 30;

    private final static char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p',
            'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    private final static String BASE32 = "0123456789bcdefghjkmnpqrstuvwxyz";
    private final static HashMap<Character, Integer> lookup = new HashMap<>();

    //计算附近区域
    private static Map<String, String> BORDERS = new HashMap<>();
    private static Map<String, String> NEIGHBORS = new HashMap<>();

    static {
        int i = 0;
        for (char c : digits)
            lookup.put(c, i++);

        NEIGHBORS.put("right:even", "bc01fg45238967deuvhjyznpkmstqrwx");
        NEIGHBORS.put("left:even", "238967debc01fg45kmstqrwxuvhjyznp");
        NEIGHBORS.put("top:even", "p0r21436x8zb9dcf5h7kjnmqesgutwvy");
        NEIGHBORS.put("bottom:even", "14365h7k9dcfesgujnmqp0r2twvyx8zb");

        NEIGHBORS.put("right:odd", "p0r21436x8zb9dcf5h7kjnmqesgutwvy");
        NEIGHBORS.put("left:odd", "14365h7k9dcfesgujnmqp0r2twvyx8zb");
        NEIGHBORS.put("top:odd", "bc01fg45238967deuvhjyznpkmstqrwx");
        NEIGHBORS.put("bottom:odd", "238967debc01fg45kmstqrwxuvhjyznp");

        BORDERS.put("right:even", "bcfguvyz");
        BORDERS.put("left:even", "0145hjnp");
        BORDERS.put("top:even", "prxz");
        BORDERS.put("bottom:even", "028b");

        BORDERS.put("right:odd", "prxz");
        BORDERS.put("left:odd", "028b");
        BORDERS.put("top:odd", "bcfguvyz");
        BORDERS.put("bottom:odd", "0145hjnp");

    }


    public static void main(String[] args) throws Exception {
       /* String s = GeoHash.encode(31.20666667, 121.4836111);
        System.out.println(s);
        double[] points = GeoHash.decode(s);
        System.out.println(points[0] + ":" +points[1]);

        String[] result = getGeoHashExpand(s.substring(0, 6));

        for (String str: result)
            System.out.println(str);
        
        GeoTool tool = new GeoTool(); */ 
        System.out.println(getDistance(121.594714, 31.26872, 121.584714, 31.26872));
       /* System.out.println(getPointDistance(31.26872, 121.594714, 31.26872, 121.584714));  */
    }

    public static double[] decode(String geoHash) {
        StringBuilder buffer = new StringBuilder();

        for (char c : geoHash.toCharArray()) {
            int i = lookup.get(c) + 32;
            buffer.append(Integer.toString(i, 2).substring(1));
        }

        BitSet lonSet = new BitSet();
        BitSet latSet = new BitSet();
        //even bits
        int j = 0;
        for (int i = 0; i < numBits * 2; i += 2) {
            boolean isSet = false;
            if (i < buffer.length())
                isSet = buffer.charAt(i) == '1';
            lonSet.set(j++, isSet);
        }

        //odd bits
        j = 0;
        for (int i = 1; i < numBits * 2; i += 2) {
            boolean isSet = false;
            if (i < buffer.length())
                isSet = buffer.charAt(i) == '1';
            latSet.set(j++, isSet);
        }

        double lon = decode(lonSet, -180, 180);
        double lat = decode(latSet, -90, 90);

        return new double[]{lat, lon};
    }

    private static double decode(BitSet bs, double floor, double ceiling) {
        double mid = 0;
        for (int i = 0; i < bs.length(); i++) {
            mid = (floor + ceiling) / 2;
            if (bs.get(i))
                floor = mid;
            else
                ceiling = mid;
        }
        return mid;
    }

    public static String encode(double lat, double lon) {
        BitSet latbits = getBits(lat, -90, 90);
        BitSet lonbits = getBits(lon, -180, 180);
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < numBits; i++) {
            buffer.append((lonbits.get(i)) ? '1' : '0');
            buffer.append((latbits.get(i)) ? '1' : '0');
        }
        return base32(Long.parseLong(buffer.toString(), 2));
    }

    private static BitSet getBits(double lat, double floor, double ceiling) {
        BitSet buffer = new BitSet(numBits);
        for (int i = 0; i < numBits; i++) {
            double mid = (floor + ceiling) / 2;
            if (lat >= mid) {
                buffer.set(i);
                floor = mid;
            } else {
                ceiling = mid;
            }
        }
        return buffer;
    }

    public static String base32(long i) {
        char[] buf = new char[65];
        int charPos = 64;
        boolean negative = (i < 0);
        if (!negative)
            i = -i;
        while (i <= -32) {
            buf[charPos--] = digits[(int) (-(i % 32))];
            i /= 32;
        }
        buf[charPos] = digits[(int) (-i)];

        if (negative)
            buf[--charPos] = '-';
        return new String(buf, charPos, (65 - charPos));
    }

    public static double getDistance(double MLonA, double MLatA, double MLonB, double MLatB) {
        double R = 6378137; // 地球半径
        double lat1 = MLatA * Math.PI / 180.0;
        double lat2 = MLatB * Math.PI / 180.0;
        double a = lat1 - lat2;
        double b = (MLonA - MLonB) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));
        return d;
    }

    /***********************获取九个的矩形编码****************************************/



//    public static void setMap() {
//        NEIGHBORS.put("right:even", "bc01fg45238967deuvhjyznpkmstqrwx");
//        NEIGHBORS.put("left:even", "238967debc01fg45kmstqrwxuvhjyznp");
//        NEIGHBORS.put("top:even", "p0r21436x8zb9dcf5h7kjnmqesgutwvy");
//        NEIGHBORS.put("bottom:even", "14365h7k9dcfesgujnmqp0r2twvyx8zb");
//
//        NEIGHBORS.put("right:odd", "p0r21436x8zb9dcf5h7kjnmqesgutwvy");
//        NEIGHBORS.put("left:odd", "14365h7k9dcfesgujnmqp0r2twvyx8zb");
//        NEIGHBORS.put("top:odd", "bc01fg45238967deuvhjyznpkmstqrwx");
//        NEIGHBORS.put("bottom:odd", "238967debc01fg45kmstqrwxuvhjyznp");
//
//        BORDERS.put("right:even", "bcfguvyz");
//        BORDERS.put("left:even", "0145hjnp");
//        BORDERS.put("top:even", "prxz");
//        BORDERS.put("bottom:even", "028b");
//
//        BORDERS.put("right:odd", "prxz");
//        BORDERS.put("left:odd", "028b");
//        BORDERS.put("top:odd", "bcfguvyz");
//        BORDERS.put("bottom:odd", "0145hjnp");
//
//    }

    /**获取九个点的矩形编码
     * @param geohash 位置
     * @return 不知道
     */
    public static String[] getGeoHashExpand(String geohash){
        try {
            String geohashTop = calculateAdjacent(geohash, "top");
            String geohashBottom = calculateAdjacent(geohash, "bottom");
            String geohashRight = calculateAdjacent(geohash, "right");
            String geohashLeft = calculateAdjacent(geohash, "left");
            String geohashTopLeft = calculateAdjacent(geohashLeft, "top");
            String geohashTopRight = calculateAdjacent(geohashRight, "top");
            String geohashBottomRight = calculateAdjacent(geohashRight, "bottom");
            String geohashBottomLeft = calculateAdjacent(geohashLeft, "bottom");
            String[] expand = {geohash, geohashTop, geohashBottom, geohashRight, geohashLeft, geohashTopLeft, geohashTopRight, geohashBottomRight, geohashBottomLeft};
            return expand;
        } catch (Exception e) {
            return null;
        }
    }

    /**分别计算每个点的矩形编码
     * @param srcHash 不知道
     * @param dir 目录
     * @return 基本路径
     */
    public static String calculateAdjacent(String srcHash, String dir) {
        srcHash = srcHash.toLowerCase();
        char lastChr = srcHash.charAt(srcHash.length()-1);
        int a = srcHash.length()%2;
        String type = (a>0)?"odd":"even";
        String base = srcHash.substring(0,srcHash.length()-1);
        if (BORDERS.get(dir+":"+type).indexOf(lastChr)!=-1){
            base = calculateAdjacent(base, dir);
        }
        base = base + BASE32.toCharArray()[(NEIGHBORS.get(dir+":"+type).indexOf(lastChr))];
        return base;
    }

    private static final double EARTH_RADIUS = 6378.137;  

    public static double getPointDistance(double lat1,double lng1,double lat2,double lng2){  
        double result = 0 ;  
          
        double radLat1 = radian(lat1);  
          
        double ratlat2 = radian(lat2);  
        double a = radian(lat1) - radian(lat2);  
        double b = radian(lng1) - radian(lng2);  
          
        result = 2*Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2)+Math.cos(radLat1)*Math.cos(ratlat2)*Math.pow(Math.sin(b/2), 2)));  
        result = result*EARTH_RADIUS;     
      
        result = Math.round(result*1000);   //返回的单位是米，四舍五入  
          
        return result;  
    }  

    private static double radian(double d){  
        return (d*Math.PI)/180.00;  
    }  
  
}