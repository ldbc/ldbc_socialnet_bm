/*
 * Copyright (c) 2013 LDBC
 * Linked Data Benchmark Council (http://ldbc.eu)
 *
 * This file is part of ldbc_socialnet_dbgen.
 *
 * ldbc_socialnet_dbgen is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ldbc_socialnet_dbgen is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ldbc_socialnet_dbgen.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2011 OpenLink Software <bdsmt@openlinksw.com>
 * All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation;  only Version 2 of the License dated
 * June 1991.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package ldbc.socialnet.dbgen.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;

public class Distribution {
    

    double[] distribution;
    String distributionFile;
    
    public Distribution( String distributionFile ){
        this.distributionFile = distributionFile;
    }

    public void initialize() {
        try{
		    BufferedReader distributionBuffer = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(distributionFile), "UTF-8"));
            ArrayList<Double> temp = new ArrayList<Double>();
		    String line;
		    while ((line = distributionBuffer.readLine()) != null){
                Double prob = Double.valueOf(line);
                temp.add(prob);
            }
            distribution = new double[temp.size()];
            int index = 0;
            Iterator<Double> it = temp.iterator();
            while(it.hasNext()) {
                distribution[index] = it.next();
                ++index;
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    private int binarySearch( double prob ) {
        int upperBound = distribution.length - 1;
        int lowerBound = 0;
        int midPoint = (upperBound + lowerBound)  / 2;
        while (upperBound > (lowerBound+1)){
            if (distribution[midPoint] > prob ){
                upperBound = midPoint;
            } else{
                lowerBound = midPoint; 
            }
            midPoint = (upperBound + lowerBound)  / 2;
        }
        return midPoint;
    }

    public double nextDouble( Random random) {
        return (double)binarySearch(random.nextDouble())/(double)distribution.length;
    }
}
