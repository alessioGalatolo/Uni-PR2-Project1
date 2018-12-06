# Uni-PR2-Project1

La prima implementazione è realizzata con una HashTable che usa il nome utente come chiave e la coppia
password-lista_di_dati (realizzata con una classe interna) come valore associato. L'hashtable è inizializzata con loadFactor di 0.5 in
modo da aumentare il consumo di spazio e diminuire la probabilità di collisioni. In questo modo si possono considerare le operazioni di ricerca, aggiunta e rimozione con tempo costante
La seconda implementazione è realizzata con 3 arrayList in modo da avere la ricerca e l'acesso ai dati con
metodi standard. Il consumo di spazio è minore rispetto all'hashTable ma tutti i metodi hanno tempo lineare.
Nell'interfaccia sono introdotte 3 nuove eccezioni nel caso si cerchi di aggiungere un utente già presente, interagire con
dati non presenti, o vengano forniti un username-password discordanti. Vengono inoltre lanciate due
eccezioni unchecked già presenti in java: NullPointerException, UnsupportedOperationException.
Sempre nell'interfaccia è presente un classe che estende Iterator negando l'accesso al metodo remove.

