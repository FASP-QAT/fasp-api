/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 30-Jan-2023
 */

UPDATE ap_static_label_languages
LEFT JOIN ap_static_label ON ap_static_label.STATIC_LABEL_ID=ap_static_label_languages.STATIC_LABEL_ID
SET ap_static_label_languages.LABEL_TEXT='The parent node values feed into the current node. Parent nodes cannot be edited. To change the parent, delete this node and create a child node under the neat parent node.' where ap_static_label.LABEL_CODE='static.tooltip.Parent' 
and ap_static_label_languages.LANGUAGE_ID=1;

UPDATE ap_static_label_languages
LEFT JOIN ap_static_label ON ap_static_label.STATIC_LABEL_ID=ap_static_label_languages.STATIC_LABEL_ID
SET ap_static_label_languages.LABEL_TEXT='Les valeurs du nœud parent alimentent le nœud actuel. Les nœuds parents ne peuvent pas être modifiés. Pour changer le parent, supprimez ce nœud et créez un nœud enfant sous le nœud parent soigné.' where ap_static_label.LABEL_CODE='static.tooltip.Parent' 
and ap_static_label_languages.LANGUAGE_ID=2;

UPDATE ap_static_label_languages
LEFT JOIN ap_static_label ON ap_static_label.STATIC_LABEL_ID=ap_static_label_languages.STATIC_LABEL_ID
SET ap_static_label_languages.LABEL_TEXT='Los valores del nodo principal alimentan el nodo actual. Los nodos principales no se pueden editar. Para cambiar el nodo principal, elimine este nodo y cree un nodo secundario debajo del nodo principal ordenado.' where ap_static_label.LABEL_CODE='static.tooltip.Parent' 
and ap_static_label_languages.LANGUAGE_ID=3;

UPDATE ap_static_label_languages
LEFT JOIN ap_static_label ON ap_static_label.STATIC_LABEL_ID=ap_static_label_languages.STATIC_LABEL_ID
SET ap_static_label_languages.LABEL_TEXT='Os valores do nó pai alimentam o nó atual. Os nós pais não podem ser editados. Para alterar o pai, exclua esse nó e crie um nó filho sob o nó pai puro.' where ap_static_label.LABEL_CODE='static.tooltip.Parent' 
and ap_static_label_languages.LANGUAGE_ID=4;