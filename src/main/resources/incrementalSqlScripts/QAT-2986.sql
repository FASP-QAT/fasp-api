/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  rohit
 * Created: May 31, 2023
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.commit.consumptionnotesvalid','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Only letters, spaces, numbers and special characters are allowed. Please check for invalid characters.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Seuls les lettres, des espaces, chiffres et caractères spéciaux sont autorisés. Veuillez vérifier les caractères non valides.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Solo se permiten letras, espacios, números y caracteres especiales. Compruebe si hay caracteres no válidos.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Somente letras, espaços, números e caracteres especiais são permitidos. Verifique se há caracteres inválidos.');-- pr