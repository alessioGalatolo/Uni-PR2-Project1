# Uni-PR2-Project1

La prima implementazione è realizzata usando una HashTable che usa il nome utente come chiave e la coppia
(Pair) password-lista di dati come valore associato. L'hashtable è inizializzata con loadFactor di 0.5 in
modo da aumentare il consumo di spazio e aumentare la probabilità di operazioni in tempo costante. Quindi
tutti i metodi implementati hanno tempo costante.
La seconda implementazione è realizzata con 3 arrayList in modo da avere la ricerca e l'acesso ai dati con
metodi standard. Il consumo di spazio è minore rispetto all'hashTable ma tutti i metodi hanno tempo lineare.
Sono Introdotte 3 nuove eccezioni nel caso si cerchi di aggiungere un utente già presente, interagire con
dati non presenti, o vengono forniti un username-password discordanti. Vengono inoltre lanciate due
eccezioni unchecked già presenti in java: NullPointerException, UnsupportedOperationException.
Il progetto è stato realizzato usando java 9.

