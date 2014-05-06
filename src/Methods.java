import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;


public class Methods {
    short codigo = 0;
    List<Juego> juegos = new ArrayList<Juego>();
    List<Jugador> jugadores = new ArrayList<Jugador>();

    public sNuevo nuevo(String designation, int maximum) {
        sNuevo out = new sNuevo();
        if (buscarNombre(designation) == false) {
            codigo++;
            Juego game = new Juego();
            game.designation = designation;
            game.code = codigo;
            game.maximum = maximum;
            game.Jugando = new ArrayList<Jugador>();
            juegos.add(game);
            out.code = codigo;
            out.error = Data.OK;
            return out;
        } else {
            out.code = 0;
            out.error = Data.ALREADY_EXISTS;
            return out;
        }
    }

    public int quita(short code) {
        int index = 0;
        if (buscarCodigo(code, index)) {
            juegos.remove(index);
            return Data.OK;
        } else {
            return Data.ALREADY_EXISTS;
        }
    }

    public int inscribe(String name, String alias) {
        //printhelp(jugadores);  // PRINT
        Jugador player = new Jugador();
        player.alias = alias;
        player.name = name;
        int index = 0;
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
        for (int ii = 0; ii < juegos.size(); ii++) {
            Juego game = it.next();
            if (game.maximum > minimum) {
                repert.add(game);
            }
        }
        return repert;
    }

    public int juega(String alias, short code) {
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
            index = 0;
            if (buscarCodigo(code, index)) {
                Juego game = juegos.get(index);
                index = 0;
                if (!buscarJugando(alias, game, index)) {
                    game.Jugando.add(player);
                }
                return Data.OK;
            } else {
                return Data.DOESNT_EXIST;
            }
        } else {
            return Data.DOESNT_EXIST;
        }
    }

    public int termina(String alias, short code) {
		/*Answer answer = new Answer();
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
		}*/
        int index = 0;
        if (buscarJugador(alias, index)) {
            Jugador player = jugadores.get(index);
            index = 0;
            if (buscarCodigo(code, index)) {
                Juego game = juegos.get(index);
                index = 0;
                if (!buscarJugando(alias, game, index)) {
                    game.Jugando.remove(index);
                }
                return Data.OK;
            } else {
                return Data.DOESNT_EXIST;
            }
        } else {
            return Data.DOESNT_EXIST;
        }
    }

    public List<Jugador> lista(short code) {
		/*Answer answer = new Answer();
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
		//return lista_final;*/
        int index = 0;
        if (buscarCodigo(code, index)) {
            Juego game = juegos.get(index);
            return game.Jugando;
        } else {
            return new ArrayList<Jugador>();
        }
    }

	/* ====================================
	 * 			AUXILIARES
	 * ====================================
	 */

    private Boolean buscarNombre(String contenido) {
        Boolean result = false;
        try {
            ListIterator<Juego> it = juegos.listIterator();
            for (int ii = 0; ii < juegos.size(); ii++) {
                if (it.next().designation.equals(contenido)) {
                    return true;
                }
            }
        } catch (NullPointerException e) {
            System.out.println("fallo");
        }
        return result;
    }

    private Boolean buscarCodigo(short code, int index) {
        Boolean result = false;
        try {
            ListIterator<Juego> it = juegos.listIterator();
            for (int ii = index; ii < juegos.size(); ii++) {
                if (it.next().code == code) {
                    index = ii;
                    return true;
                }
            }
        } catch (NullPointerException e) {
            System.out.println("fallo");
        }
        return result;
    }

    private Boolean buscarJugador(String alias, int index) {
        Boolean result = false;
        try {
            ListIterator<Jugador> it = jugadores.listIterator();
            for (int ii = index; ii < jugadores.size(); ii++) {
                if (it.next().alias == alias) {
                    index = ii;
                    return true;
                }
            }
        } catch (NullPointerException e) {
            System.out.println("fallo");
        }
        return result;
    }

    private Boolean buscarJugando(String alias, Juego game, int index) {
        Boolean result = false;
        List<Jugador> playing = game.Jugando;
        try {
            ListIterator<Jugador> it = playing.listIterator();
            for (int ii = index; ii < playing.size(); ii++) {
                if (it.next().alias == alias) {
                    index = ii;
                    return true;
                }
            }
        } catch (NullPointerException e) {
            System.out.println("fallo");
        }
        return result;
    }

    private void printhelp(List<String> fich) {
        int ii = 0;
        System.out.println("=======START OF LIST=======");
        //System.out.println(fich);
        for (ii = 0; ii < fich.size(); ii++) {
            System.out.println(fich.get(ii));
        }
        System.out.println("========END OF LIST========");
    }
}

class Juego {
    short code;
    String designation;
    int maximum;
    List<Jugador> Jugando;
}

class Jugador {
    String name;
    String alias;
}

class sNuevo {
    short code;
    int error;
}