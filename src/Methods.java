import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class Methods {
	short codigo = 0;
	
	public int nuevo(String designation, int maximum) {
		String path_fichero = "~/lsodtr/prueba.txt";
		if (buscar(designation, path_fichero) == false) {
			String contenido = designation + " -> " + Integer.toString(maximum);
			System.out.println(contenido);
			escribir(contenido, path_fichero);
			return codigo;
		} else {
			return Data.ALREADY_EXISTS;
		}
	}
	
	public int quita(short code) {
		String path_fichero = "";
		if (buscar(code, path_fichero) != null) {
			String lineToRemove = buscar(code, path_fichero);
			removeLineFromFile(lineToRemove, path_fichero);
			return Data.OK;
		}
		return Data.DOESNT_EXIST;
	}
	
	public int inscribe(String name, String alias) {
		String path_fichero = "";
		if (buscar(name, path_fichero) && buscar(alias, path_fichero) == false) {
			String contenido = name + " -> " + alias;
			System.out.println(contenido);
			escribir(contenido, path_fichero);
			return Data.OK;
		} else {
			return Data.ALREADY_EXISTS;
		}
	}
	
	public String plantilla() {
		String path_fichero = "";
		ArrayList<String> plantilla = null;
		plantilla = leer(path_fichero);
		Collections.sort(plantilla);
		String plantilla_final = plantilla.toString();
		return plantilla_final;
	}
	
	public String repertorio(byte minimum) {
		String path_fichero = "";
		ArrayList<String> repertorio = null;
		repertorio = leer(path_fichero);
		int min = minimum;
		for(String temp : repertorio) {
			String[] parts = temp.split(" ");
			int part1 = Integer.parseInt(parts[1]);
			if(part1<min) {
				repertorio.remove(temp);
			}
		}
		return repertorio.toString();
	}
	
	public int juega(String alias, short code) {
		String path_fichero = "";
		File file = new File(path_fichero);
		try {
			replaceSelected(file, code);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}/*
		ArrayList<String> juegos = null;
		juegos = leer(path_fichero);
		for(String temp : juegos) {
			String[] parts = temp.split(" ");
			int part0 = Integer.parseInt(parts[0]);
			if(part0==code) {
				
				//juegos.
			}
		}*/
		
		return 0;
	}
	
	public int termina(String alias, short code) {
		return 0;
	}
	
	public int lista(short code) {
		return 0;
	}

	/* ====================================
	 * 			AUXILIARES
	 * ====================================
	 */
	private void escribir(String contenido, String nombre_fichero) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(nombre_fichero);
            pw = new PrintWriter(fichero);
 
            for (int i = 0; i < 10; i++) {
                pw.println(codigo + " " + contenido);
            	codigo ++;
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	try {
        		if (null != fichero)
        		   fichero.close();
        	} catch (Exception e2) {
        		e2.printStackTrace();
        	}
        }
    }
	
	private ArrayList<String> leer(String path_fichero) {
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		ArrayList<String> resultado = null;
		
		try {
			archivo = new File(path_fichero);
			fr = new FileReader (archivo);
	        br = new BufferedReader(fr);
	 
	        // Lectura del fichero
	        String linea;
	        resultado = new ArrayList<String>();
	        while((linea=br.readLine())!=null)
	        	resultado.add(linea);
	         	System.out.println(linea);
	      } catch(Exception e) {
	    	  	e.printStackTrace();
	      } finally {
	         // En el finally cerramos el fichero, para asegurarnos
	         // que se cierra tanto si todo va bien como si salta 
	         // una excepcion.
	         try{                    
	            if( null != fr ){   
	               fr.close();     
	            }                  
	         }catch (Exception e2){ 
	            e2.printStackTrace();
	         }
	      }
		return resultado;
		
	}
	
	private Boolean buscar(String cadena, String path_fichero) {
		 java.util.Scanner scanner = new Scanner(cadena);
		 String request = scanner.next();
		 try {
			final BufferedReader reader = new BufferedReader(new FileReader(path_fichero));
			String line = "";
			while((line = reader.readLine())!= null) {
				if(line.indexOf(request)!= -1) {
					System.out.println(""+line);
					reader.close();
					return true;
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {e.printStackTrace();
		} catch (IOException           e) {e.printStackTrace();
		}
		scanner.close();
		return false;
	}
	
	private String buscar(int code, String path_fichero) {
		 java.util.Scanner scanner = new Scanner(Integer.toString(code));
		 String request = scanner.next();
		 try {
			final BufferedReader reader = new BufferedReader(new FileReader(path_fichero));
			String line = "";
			while((line = reader.readLine())!= null) {
				if(line.indexOf(request) == 1) {
					System.out.println(""+line);
					reader.close();
					return line;
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {e.printStackTrace();
		} catch (IOException           e) {e.printStackTrace();
		}
		scanner.close();
		return null;
	}
	
	private void removeLineFromFile(String lineToRemove, String path_fichero) {
	    try {
	        File inFile = new File(path_fichero);
	        if (!inFile.isFile()) {
	            System.out.println("no hay file");
	            return;
	        }
	        //Construct the new file that will later be renamed to the original filename.
	        File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
	        BufferedReader br = new BufferedReader(new FileReader(path_fichero));
	        PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
	        String line = null;
	        //Read from the original file and write to the new
	        //unless content matches data to be removed.
	        while ((line = br.readLine()) != null) {
	            if (!line.trim().equals(lineToRemove)) {
	                pw.println(line);
	                pw.flush();
	            }
	        }
	        pw.close();
	        br.close();
	        //Delete the original file
	        if (!inFile.delete()) {
	            System.out.println("Could not delete file");
	            return;
	        }
	        //Rename the new file to the filename the original file had.
	        if (!tempFile.renameTo(inFile)){
	            System.out.println("Could not rename file");   
	        }
	    } catch (FileNotFoundException ex) {
	        ex.printStackTrace();
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	}
	
	private void replaceSelected(File file, short code) throws IOException {

	    // we need to store all the lines
	    List<String> lines = new ArrayList<String>();

	    // first, read the file and store the changes
	    BufferedReader in = new BufferedReader(new FileReader(file));
	    String line = in.readLine();
	    while (line != null) {
	        if (line.startsWith(Integer.toString(code))) {
	            String sValue = line.substring(line.indexOf(' ')+1).trim();
	            int nValue = Integer.parseInt(sValue);
	            line = Integer.toString(code) + " " + (nValue+1);
	        }
	        lines.add(line);
	        line = in.readLine();
	    }
	    in.close();

	    // now, write the file again with the changes
	    PrintWriter out = new PrintWriter(file);
	    for (String l : lines)
	        out.println(l);
	    out.close();

	}
}