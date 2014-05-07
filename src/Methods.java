import java.util.*;


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
            Collections.sort(juegos, new gameComparator());
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
            Collections.sort(jugadores, new playerComparator());
            return Data.OK;
        } else {
            return Data.ALREADY_EXISTS;
        }
    }

    public List<Jugador> plantilla() {
        return jugadores;
    }

    public List<Juego> repertorio(byte minimum) {
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

class gameComparator implements Comparator<Juego> {
    @Override
    public int compare(Juego o1, Juego o2) {
        return o1.designation.compareTo(o2.designation);
    }
}

class playerComparator implements Comparator<Jugador> {
    @Override
    public int compare(Jugador o1, Jugador o2) {
        return o1.alias.compareTo(o2.alias);
    }
}