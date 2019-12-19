# Project STORE

Il s'agit d'un projet d'API sur SpringBoot avec JDBC template.
On parle d'un projet de gestion d'inventaire de magasins. 

## Les étapes du projet : 

### Partie 1
- [ ] Pouvoir créer des magasins valides (avec un nom non vide)
- [ ] Pouvoir visualiser un magasin existant
- [ ] Pouvoir supprimer un magasin existant
- [ ] BONUS Pouvoir mettre à jour un magasin existant (modifier son nom)

### Partie 2
- [ ] Pouvoir ajouter des objets en stock à un magasin (nom non vide, valeur non vide et > 0)
- [ ] Pouvoir supprimer des objets du stock d'un magasin
- [ ] Pouvoir récuppérer la liste de tous les objets d'un magasin 
- [ ] Pouvoir récuppérer la valeur du stock d'un magasin comme propriété d'un magasin

#### CREATE 

Un objet de stock se créé sur la route `/stores/{store_id}/stocks`

Un objet de stock doit avoir pour être valid : 
- la propriété `name` qui ne doit pas être vide
- la propriété `type` qui vaut soit `Fruit` soit `Nail`
- la propriété `value` qui est en centimes d'euro et doit être `> 0` et être un `Int`
- la propriété `store_id` qui doit pointer vers un magasin qui existe
#### GET

 Un objet de stock se récupère sur la route `/stores/{store_id}/stocks/{id}`.
 Il faut vérifier que l'objet de stock appartient bien au magasin dont l'id est dans l'url.
 Si ce n'est pas le cas, renvoyer `Unauthorized` ou `404`.
 
#### UPDATE

Il faut à la fois vérifier que l'objet soit valide et le règle d'appartenance au magasin de l'url.
 
#### DELETE

Il faut à la fois vérifier la règle d'appartenance au magasin de l'url.
 
 
### Partie 3
- [ ] Pouvoir récuppérer la liste de tous les objets d'un magasin d'un certain type
- [ ] Pouvoir récupérer tous les magasins dont la valeur du stock dépasse un certain montant
- [ ] Empecher de pouvoir rajouter plus de 4 fruits en stock dans un même magasin au même moment

## Règles du jeu : 

- Pour chacune des fonctionnalités, il faut qu'elle soit testée.
- Les controlleurs ne communiquent qu'avec la couche service, seule la couche service peut communiquer
 avec les repositories.
- Les controlleurs communiquent en JSON. 



 
