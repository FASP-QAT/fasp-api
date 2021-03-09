/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 05-Mar-2021
 */

INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.commitVersion.versionIsOutDated','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Loaded version is out dated. Please refresh the page.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La version chargée est obsolète. Veuillez actualiser la page.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La versión cargada está desactualizada. Actualice la página.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A versão carregada está desatualizada. Atualize a página.');-- pr

delete b.* from rm_consumption_trans_batch_info b where b.CONSUMPTION_TRANS_ID in (44391,44392,44393,44394,44395,44396,44397,44398,44399,44400,44401,44402,44403,44404,44405,44406,44407,44408,44409,44410,44411,44412,44413,44414,44415,44416,44417,44418,44419,44420,44421,44422,44423,44424,44425,44426);

delete c.* from rm_consumption_trans c where c.CONSUMPTION_TRANS_ID in (44391,44392,44393,44394,44395,44396,44397,44398,44399,44400,44401,44402,44403,44404,44405,44406,44407,44408,44409,44410,44411,44412,44413,44414,44415,44416,44417,44418,44419,44420,44421,44422,44423,44424,44425,44426);

delete c.* from rm_consumption c where c.CONSUMPTION_ID in (39009,39010,39011,39012,39013,39014,39015,39016,39017,39018,39019,39020,39021,39022,39023,39024,39025,39026,39027,39028,39029,39030,39031,39032,39033,39034,39035,39036,39037,39038,39039,39040,39041,39042,39043,39044);