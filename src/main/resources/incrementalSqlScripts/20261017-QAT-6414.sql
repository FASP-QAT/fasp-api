/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 17-Oct-2026
 */

INSERT INTO ap_static_label VALUES (null, 'static.tree.calculatedNodeValue16DigitLimit', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO ap_static_label_languages VALUES (null, @label_id, 1, 'Please reduce the percentage node value so the calculated node value remains within the 16-digit limit.');
INSERT INTO ap_static_label_languages VALUES (null, @label_id, 2, 'Veuillez réduire la valeur du nœud en pourcentage afin que la valeur calculée du nœud reste dans la limite de 16 chiffres.');
INSERT INTO ap_static_label_languages VALUES (null, @label_id, 3, 'Reduzca el valor del nodo porcentual para que el valor calculado del nodo se mantenga dentro del límite de 16 dígitos.');
INSERT INTO ap_static_label_languages VALUES (null, @label_id, 4, 'Reduza o valor percentual do nó para que o valor calculado do nó permaneça dentro do limite de 16 dígitos.');
