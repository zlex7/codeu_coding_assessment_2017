// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.google.codeu.codingchallenge;

import java.io.IOException;
import java.util.regex.*;
final class MyJSONParser implements JSONParser {
	private String input;
	private int index;
  @Override
  public JSON parse(String in) throws IOException {
    // TODO: implement this
    input = in.trim();
		//System.out.println("----------");
  //  System.out.println("input: " + input);
  //System.out.println(input.length());
    index=1;
    /*
    MyJSON data = new MyJSON();
    Pattern p = Pattern.compile("\\\"|\"(?:\"|[^\"])*\"|(:)");
    Matcher m = p.matcher(in);
    while(m.find()){
      m.group();
      int index = m.start();
      System.out.println(index);
    }
    */
		skipWhiteSpace();
		if(input.charAt(0)=='{'){
    	JSON data = parseRecursive();
			if(index<input.length()){
				if(Pattern.compile("\\S").matcher(input.substring(index)).find()){
					throw new IOException("extra characters at end of input");
				}
				else{
					return data;
				}
			}
			else{
				return data;
			}

		}
		else{
			throw new IOException("extra characters at beginning of input");
		}
    }


//This method does all of the real work
//It's a little sketchy, but it seems to work fine
  public JSON parseRecursive() throws IOException{
	  MyJSON data = new MyJSON();
	  skipWhiteSpace();
	  int numEntries = 0;
	    while(input.charAt(index)!='}'){

	    	skipWhiteSpace();

	    	if(numEntries>0){
	    		if(input.charAt(index)!=','){
	    			throw new IOException("no comma in between key:value pair at index " + index);
	    		}
	    		else{
	    			index++;
	    		}
	    	}

	    	skipWhiteSpace();

	    	String key = getString();
	    //	System.out.println("key: " + key);

	    	skipWhiteSpace();

	    	if(input.charAt(index)!=':'){
	    		//System.out.println(input.substring(index));
	    		throw new IOException("no colon in between key and value at index " + index);
	    	}
	    	else{
	    		index++;
	    	}

	    	skipWhiteSpace();

				//this is the starting character of the value section of key:value
				//it lets the program know how to handle the value
	    	char beginningValue = input.charAt(index);

	        if(beginningValue=='"'){
	        	String value = getString();
	        	//System.out.println("string value: " + value);
	        	numEntries++;

						//checking for duplicate keys
						if(data.strings.containsKey(key) || data.objects.containsKey(key)){
							throw new IOException("duplicate key at index " + index);
						}
						else{
							//I know I can use the setString() method I defined in MyJSON.java, but I like this because it's more explicit
	        	data.strings.put(key,value);
						}
	        }

	        else if(beginningValue=='{'){
	        	index++;
	        //	System.out.println(input.substring(index));
	        	numEntries++;
						if(data.strings.containsKey(key) || data.objects.containsKey(key)){
							throw new IOException("duplicate key at index " + index);
						}
						else{
	        	data.objects.put(key, parseRecursive());
					  }
	        }

	        skipWhiteSpace();

	      //  System.out.println("index: " + index);
	      }

				index++;

	    //System.out.println(data.strings);
	    //System.out.println(data.objects);

	    return data;

  }


	//obviously this is used to skip whitespace in the input
  public void skipWhiteSpace(){
	 char next = input.charAt(index);
	 while(Character.isWhitespace(next)){
		 next = input.charAt(++index);
	 }

  }


//this is used to get a string when required
//it also checks for string validity. Correctly escaped characters, etc.
  public String getString() throws IOException{
	  int startIndex = index+1;
	  String str="";
	  if(input.charAt(index)=='"'){
  	while(true){
  		index++;
  		if(input.charAt(index)=='\\'){
  			char afterSlash = input.charAt(index+1);
  			if(afterSlash=='\\' || afterSlash=='"' || afterSlash=='n' || afterSlash=='t'){
  				index++;
  			}
  			else{
  				throw new IOException("invalid escaped character in string at index " + index);
  			}
  		}
  		else if(input.charAt(index)=='"'){
  			str = input.substring(startIndex,index);
  			break;
  			}
  		}
	  }
  else {
	 // System.out.println(input.substring(index));
	  throw new IOException("invalid input. please try again. index " + index);
	  }
  	index++;
  	return str;
  }

}
