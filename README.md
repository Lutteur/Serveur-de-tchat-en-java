# 🌐​ Serveur de tchat en Java

## 📁 ​Le projet

Le but de ce projet académique était de concevoir un serveur de
tchat multi-clients en Java, où un serveur se lance et plusieurs
Clients peuvent se connecter avec un port spécifique.

## Pour lancer et se connecter au serveur

- Compiler puis exécuter la classe `TestServeurSimple.java`

- Dans un autre terminal, exécuter la commande `ncat localhost numéro_port`

- Vous entrez votre nom et vous êtes connecté

Pour vous déconnecter : `Ctrl + c`.

Le numéro de port est 6000 mais vous pouvez mettre le port de votre choix

`ncat` est la commande sous windows, `nc` est la commande sous linux

> ⚠️Si la commande `ncat` ne marche, c'est probablement qu'elle n'est pas installée,
vous devrez donc l'installer ou utiliser `telnet` à la place, mais je déconseille
fortement car elle peut apporter de nombreux bugs.

## 🗂️​ Les Classes Java et leurs fonctions

### ServeurSimple.java

- Lance un Socket sur le port 6000, attends qu'un client se connecte.

- Pour chaque client connecté, il y a la création d'un objet GerantClient.

- La création d'une Thread lance le client dedans à l'intérieur.

- Continue à écouter d'autres clients.

> Remarque : La Thread permet de gérer plusieurs clients en même temps.

### TestServeurSimple.java

- Contient un main qui crée un nouveau ServeurSimple.

### Client.java

- Possède deux attributs `out` de type PrintWritter et `nom` de type String.

> L'attribut `out` représente la sortie de ce que le client a tapé dans le chat.
> L'attribut `nom` représente le nom du client.

> Il y a des guetteurs pour chaque attribut

### GerantDeClient

On implémente Runnable pour pouvoir se servir de Thread.

`sClient` : attribut de type Socket qui fait référence au Socket de la classe ServeurSimple

`lstClientPartage` : attribut de type ArrayList de Client qui fait référence à la liste de la classe ServeurSimple

`nom` : attribut de type String qui fait référence au nom du client.

- supprimerClient() : supprime le client de la liste lorsqu'il se déconnecte pour libérer de l'espace

- afficherMessage() : permet d'avoir un affichage clair du client qui l'a envoyé

- run() : méthode principale de la classe Gérant de client

### La méthode run de GerantDeClient

Variables Locales :

`boK` de type booléen qui permet l'ouverture en continu du serveur

`in` de type BufferedReader qui permet de lire le message du client

`out` de type PrintWriter qui sert à afficher quelque chose au client

Cette méthode est le cœur de la classe GerantDeClient.
Lorsqu'un client est connecté, elle lui demande un nom d'utilisateur.
On ajoute ensuite ce nouveau client à la liste de clients présents.

Ensuite, on attend que le client entre un message, si le message est null c'est que le client ne répond pas,
Ou alors qu'il s'est déconnecté, dans ce cas-là on affiche aux autres clients qu'il a quitté le serveur.
Dans l'autre cas si l'utilisateur rentre seulement des entrées ou des espaces alors on ignore.
Enfin si le message est bien valide alors on fait une boucle qui parcours les clients dans le but
d'envoyer le message de ce client à tous les clients (sauf à lui-même).

> Le client est supprimé dès qu'il quitte le serveur à l'aide de `Ctrl + c`
