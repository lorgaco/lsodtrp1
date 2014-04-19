import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Methods {
	short codigo = 0;
	List<String> juegos = new ArrayList<String>();
	List<String> jugadores = new ArrayList<String>();
	List<String> playing = new ArrayList<String>();
	
	public Answer nuevo(String designation, int maximum) {
		Answer answer = new Answer();
		if (buscar2(designation, juegos) == false) {
			String contenido = Integer.toString(codigo) + " <- " + designation + " -> " + Integer.toString(maximum);
			codigo++;
			escribir2(contenido, juegos);
			answer.setAnswer(Integer.toString(codigo-1));
			answer.setError(Data.OK);
			return answer;
		} else {
			answer.setAnswer(null);
			answer.setError(Data.ALREADY_EXISTS);
			return answer;
		}
	}
	
	public Answer quita(short code) {
		Answer answer = new Answer();
		System.out.print("SIZE = " + juegos.size() + " CODE = " + code);
		int ii = 0;
		for (ii=0; ii<juegos.size(); ii++) {
			if(juegos.get(ii).startsWith(Integer.toString(code))){
				juegos.remove(ii);
				answer.setAnswer(null);
				answer.setError(Data.OK);
				return answer;
			}
		}
		answer.setAnswer(null);
		answer.setError(Data.DOESNT_EXIST);
		return answer;
	}
	
	public Answer inscribe(String name, String alias) {
		Answer answer = new Answer();
		printhelp(jugadores);
		if (!buscar2(name, jugadores) && !buscar2(alias, jugadores)) {
			String contenido = name + " : " + alias;
			System.out.println(contenido);
			escribir2(contenido, jugadores);
			answer.setAnswer(null);
			answer.setError(Data.OK);
			return answer;
		} else {
			answer.setAnswer(null);
			answer.setError(Data.ALREADY_EXISTS);
			return answer;
		}
	}
	
	public Answer plantilla() {
		Answer answer = new Answer();
		ArrayList<String> plantilla = new ArrayList<String>();
		plantilla = leer2(jugadores);
		Collections.sort(plantilla);
		String plantilla_final = plantilla.toString();
		System.out.println(plantilla_final);
		answer.setAnswer(plantilla_final);
		answer.setError(Data.OK);
		return answer;
	}
	
	public Answer repertorio(byte minimum) {
		Answer answer = new Answer();
		ArrayList<String> repertorio = new ArrayList<String>();
		repertorio = leer2(juegos);
		System.out.println(repertorio);
		int min = minimum;
		ArrayList<String> repertorio2 = new ArrayList<String>();
		for(String temp : repertorio) {
			String[] parts = temp.split(" -> ");
			int part1 = Integer.parseInt(parts[1]);
			if(part1>min) {
				repertorio2.add(temp);
				//repertorio.remove(temp);
			}
		}
		System.out.println("RESULTADO REPERTORIO");
		System.out.println(repertorio2.toString());
		answer.setAnswer(repertorio2.toString());
		answer.setError(Data.OK);
		return answer;
	}
	
	public Answer juega(String alias, short code) {
		Answer answer = new Answer();
		String contenido = Integer.toString(code) + " : " + alias;

		//System.out.println("ALIAS = " + alias);
		//System.out.println("CODE = " + Integer.toString(code));
		
		if(buscar2(alias, jugadores)) System.out.println("TRUE JUGADORES");
		if(buscar2(Integer.toString(code) + " <- ", juegos)) System.out.println("TRUE JUEGOS");
		
		if(buscar2(alias, jugadores) && buscar2(Integer.toString(code) + " <- ", juegos)) {
			//System.out.println("ENTRA 1");
			if(!buscar2(contenido, playing)) {
				//System.out.println("ENTRA 2");
				ArrayList<String> lista = new ArrayList<String>();
				lista = leer2(playing);
				Collections.sort(lista);
				int ii = 0;
				
				List<String> lista_final = new ArrayList<String>();
				lista_final = subLista(lista, code);
				
				printhelp(lista_final);
				System.out.println(" ");
				
				ArrayList<String> aux = new ArrayList<String>();
				aux = leer2(juegos);
				String aux2 = new String();
				for (ii=0; ii<aux.size(); ii++) {
					if(aux.get(ii).startsWith(Integer.toString(code))) {
						aux2 = aux.get(ii).split(" -> ")[1];
						break;
					}
				}
				int max = Integer.parseInt(aux2);
				System.out.println("MAX = " + Integer.toString(max) + " ---> LISTA_SIZE = " + Integer.toString(lista_final.size()));
				if(lista_final.size() < max) {
					//System.out.println("ENTRA 3");
					escribir2(contenido, playing);
					printhelp(playing);
					answer.setAnswer(null);
					answer.setError(Data.OK);
					return answer;
					//return Data.OK;
				} else {
					answer.setAnswer(null);
					answer.setError(Data.MAX_ACHIEVED);
					return answer;
					//return Data.MAX_ACHIEVED;
				}
			} else {
				answer.setAnswer(null);
				answer.setError(Data.OK);
				return answer;
				//return Data.OK;
			}
		} else {
			answer.setAnswer(null);
			answer.setError(Data.DOESNT_EXIST);
			return answer;
			//return Data.DOESNT_EXIST;
		}
		
		//return 0;
	}
	
	public Answer termina(String alias, short code) {
		Answer answer = new Answer();
		String contenido = Integer.toString(code) + " : " + alias;
		if(buscar2(alias, jugadores) && buscar2(Integer.toString(code) + " <- ", juegos)) {
			if(buscar2(contenido, playing)) {
				playing.remove(playing.indexOf(contenido));
				printhelp(playing);
				answer.setAnswer(null);
				answer.setError(Data.OK);
				return answer;
			} else {
				answer.setAnswer(null);
				answer.setError(Data.DOESNT_EXIST);
				return answer;
			}
		} else {
			answer.setAnswer(null);
			answer.setError(Data.DOESNT_EXIST);
			return answer;
		}
	}
	
	public Answer lista(short code) {
		Answer answer = new Answer();
		ArrayList<String> lista = new ArrayList<String>();
        ArrayList<String> lista2 = new ArrayList<String>();
		lista = leer2(playing);
        lista2 = leer2(juegos);
		Collections.sort(lista);
		printhelp(lista);
		if(buscar2(Integer.toString(code) + " <- ", lista2)) {
			List<String> lista_final = new ArrayList<String>();
            System.out.println("LISTA SIZE: " + lista.size());
            //System.out.println(lista.size());
			lista_final = subLista(lista, code);
			
			String lista_final2 = lista_final.toString();
			System.out.println(" ");
			System.out.println(" === LISTA === ");
			printhelp(lista_final);
			System.out.println(" ");
			answer.setAnswer(lista_final2);
			answer.setError(Data.OK);
			return answer;
		} else {
			answer.setAnswer(null);
			answer.setError(Data.DOESNT_EXIST);
			return answer;
		}
		//return lista_final;
	}

	/* ====================================
	 * 			AUXILIARES
	 * ====================================
	 */
	
	private void escribir2(String contenido, List<String> fich) {
		fich.add(contenido);
		printhelp(fich);
	}
	
	private ArrayList<String> leer2(List<String> fich) {
		int ii=0;
		ArrayList<String> result = new ArrayList<String>();
		for (ii=0; ii<fich.size(); ii++) {
			result.add(fich.get(ii));
		}
		printhelp(fich);
		return result;	
	}
	
	private Boolean buscar2(String contenido, List<String> fich) {
		Boolean result = false;
		int ii = 0;
		try {
			for (ii=0; ii<fich.size(); ii++) {
				if(fich.get(ii).contains(contenido)) {
                    System.out.println("BUSCAR found: --> " + contenido + " <-- in: --> " + fich.get(ii));
                    return true;
                }
			}
			//result = fich.contains(contenido);
		} catch(NullPointerException e) {
			System.out.println("fallo");
		}
		return result;		
	}
	
	private void printhelp(List<String> fich) {
		int ii=0;
		System.out.println("=======START OF LIST=======");
		//System.out.println(fich);
		for (ii=0; ii<fich.size(); ii++) {
			System.out.println(fich.get(ii));
		}
		System.out.println("========END OF LIST========");
	}
	
	private List<String> subLista(ArrayList<String> lista, short code) {
		List<String> result = new ArrayList<String>();
		
		// TODO esto se puede mejorar seguro
		int ii = 0;
		int first = 0;
		for (ii=0; ii<lista.size(); ii++) {
            // If there are more elements in the List
			if(lista.get(ii).startsWith(Integer.toString(code))) {
				first = ii;
				break;
			}
            // If it's the end of the List
            if(ii == lista.size()-1) {
                first = ii+1;
                break;
            }
		}
		int last = 0;
		for (ii=0; ii<lista.size(); ii++) {
			// If there are more elements in the List
			if(Integer.parseInt(lista.get(ii).split(" ")[0]) > code && ii >= first) {
				last = ii;
				break;
			}
            // If it's the end of the List
            if(ii == lista.size()-1) {
                last = ii+1;
                break;
            }
		}
		if(last==0 && first!=0) last=lista.size();
		// end to do (pero funciona)

		System.out.println(" ");
		System.out.println("FIRST = " + Integer.toString(first) + " >> LAST = " + Integer.toString(last));
		
		result = lista.subList(first, last);
		
		return result;
	}
}