import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;


public class Methods {
	short codigo = 0;
	List<Juego> juegos = new ArrayList<Juego>();
	List<Jugador> jugadores = new ArrayList<Jugador>();
	
	public short nuevo(String designation, byte maximum) {
		if (buscarNombre(designation) == false) {
			codigo++;
            Juego game = new Juego();
            game.designation = designation;
            game.code = codigo;
            game.maximum = maximum;
            game.Jugando = new ArrayList<Jugador>();
            juegos.add(game);
            return codigo;
		} else {
            return Data.ALREADY_EXISTS;
		}
	}
	
	public short quita(short code) {
        int index = 0;
        if (buscarCodigo(code, index)) {
            juegos.remove(index);
            return Data.OK;
        } else {
            return Data.ALREADY_EXISTS;
        }
	}
	
	public short inscribe(String name, String alias) {
		//printhelp(jugadores);  // PRINT
        int index=0;
        Jugador player = new Jugador();
        player.alias = alias;
        player.name = name;
		if (!buscarJugador(player.alias, index)) {
			jugadores.add(player);
			return Data.OK;
		} else {
			return Data.ALREADY_EXISTS;
		}
	}
	
	public List<Jugador> plantilla() {
		/*Answer answer = new Answer();
		ArrayList<String> plantilla = new ArrayList<String>();
		plantilla = leer2(jugadores);
		Collections.sort(plantilla);
		String plantilla_final = plantilla.toString();
//		System.out.println(plantilla_final);  // PRINT
		answer.setAnswer(plantilla_final);
		answer.setError(Data.OK);
		return answer;*/
        return jugadores;
	}
	
	public List<Juego> repertorio(byte minimum) {
		/*Answer answer = new Answer();
		ArrayList<String> repertorio = new ArrayList<String>();
		repertorio = leer2(juegos);
//		System.out.println(repertorio);  // PRINT
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
		System.out.println("RESULTADO REPERTORIO");  // PRINT
//		System.out.println(repertorio2.toString());  // PRINT
		answer.setAnswer(repertorio2.toString());
		answer.setError(Data.OK);
		return answer;*/
        List<Juego> repert = new ArrayList<Juego>();
        ListIterator<Juego> it = juegos.listIterator();
        for (int ii=0; ii<juegos.size(); ii++) {
            Juego game = it.next();
            if(game.maximum>minimum) {
                repert.add(game);
            }
        }
        return repert;
	}
	
	public Short juega(String alias, short code) {
		/*Answer answer = new Answer();
		String contenido = Integer.toString(code) + " : " + alias;

		//System.out.println("ALIAS = " + alias);  // PRINT
		//System.out.println("CODE = " + Integer.toString(code));  // PRINT
		
//		if(buscar2(alias, jugadores)) System.out.println("TRUE JUGADORES");  // PRINT
//		if(buscar2(Integer.toString(code) + " <- ", juegos)) System.out.println("TRUE JUEGOS");  // PRINT
		
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
				
				printhelp(lista_final);  // PRINT
				System.out.println(" ");  // PRINT
				
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
//				System.out.println("MAX = " + Integer.toString(max) + " ---> LISTA_SIZE = " +
//                        Integer.toString(lista_final.size()));  // PRINT
				if(lista_final.size() < max) {
					//System.out.println("ENTRA 3");  // PRINT
					escribir2(contenido, playing);
					printhelp(playing);  // PRINT
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
		
		//return 0;*/
        int index = 0;
        if (buscarJugador(alias, index)) {
            Jugador player = jugadores.get(index);
            if (buscarCodigo(code, index)) {
                if (buscarJugando(alias,index)){
                    juegos.get(index).Jugando.add(player);
                }
                return Data.OK;
            } else {
                return Data.DOESNT_EXIST;
            }
        } else {
            return Data.DOESNT_EXIST;
        }
	}
	
	public Answer termina(String alias, short code) {
		Answer answer = new Answer();
		String contenido = Integer.toString(code) + " : " + alias;
		if(buscar2(alias, jugadores) && buscar2(Integer.toString(code) + " <- ", juegos)) {
			if(buscar2(contenido, playing)) {
				playing.remove(playing.indexOf(contenido));
				printhelp(playing);  // PRINT
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
		printhelp(lista);  // PRINT
		if(buscar2(Integer.toString(code) + " <- ", lista2)) {
			List<String> lista_final = new ArrayList<String>();
//            System.out.println("LISTA SIZE: " + lista.size());  // PRINT
            //System.out.println(lista.size());  // PRINT
			lista_final = subLista(lista, code);
			
			String lista_final2 = lista_final.toString();
			System.out.println(" ");  // PRINT
			System.out.println(" === LISTA === ");  // PRINT
			printhelp(lista_final);  // PRINT
			System.out.println(" ");  // PRINT
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
	
	private Boolean buscarNombre(String contenido) {
		Boolean result = false;
		try {
            ListIterator<Juego> it = juegos.listIterator();
			for (int ii=0; ii<juegos.size(); ii++) {
				if(it.next().designation.equals(contenido)) {
                    return true;
                }
			}
		} catch(NullPointerException e) {
			System.out.println("fallo");
		}
		return result;		
	}

    private Boolean buscarCodigo(short code, int index) {
        Boolean result = false;
        try {
            ListIterator<Juego> it = juegos.listIterator();
            for (int ii=index; ii<juegos.size(); ii++) {
                if(it.next().code == code) {
                    index = ii;
                    return true;
                }
            }
        } catch(NullPointerException e) {
            System.out.println("fallo");
        }
        return result;
    }

    private Boolean buscarJugador(String alias, int index) {
        Boolean result = false;
        try {
            ListIterator<Jugador> it = jugadores.listIterator();
            for (int ii=index; ii<jugadores.size(); ii++) {
                if(it.next().alias == alias) {
                    index = ii;
                    return true;
                }
            }
        } catch(NullPointerException e) {
            System.out.println("fallo");
        }
        return result;
    }

    private Boolean buscarJugando(String alias, int juegoindex) {
        Boolean result = false;
        List<Jugador> playing = juegos.get(juegoindex).Jugando;
        try {
            ListIterator<Jugador> it = playing.listIterator();
            for (int ii=0; ii<playing.size(); ii++) {
                if(it.next().alias == alias) {
                    return true;
                }
            }
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

		System.out.println(" ");  // PRINT
//		System.out.println("FIRST = " + Integer.toString(first) + " >> LAST = " + Integer.toString(last));  // PRINT
		
		result = lista.subList(first, last);
		
		return result;
	}
}

class Juego {
    short code;
    String designation;
    byte maximum;
    List<Jugador> Jugando;
}

class Jugador {
    String name;
    String alias;
}