UPDATE ap_static_label_languages l 
JOIN ap_static_label s ON l.STATIC_LABEL_ID = s.STATIC_LABEL_ID 
SET l.LABEL_TEXT = REPLACE(REPLACE(l.LABEL_TEXT, '2023', '{{year}}'), '22', '{{shortYear}}') 
WHERE s.LABEL_CODE IN (
    'static.modelingValidation.fyJul', 
    'static.modelingValidation.fyAug', 
    'static.modelingValidation.fySep', 
    'static.modelingValidation.fyOct', 
    'static.modelingValidation.fyNov', 
    'static.modelingValidation.fyDec'
);

UPDATE ap_static_label_languages l 
JOIN ap_static_label s ON l.STATIC_LABEL_ID = s.STATIC_LABEL_ID 
SET l.LABEL_TEXT = REPLACE(REPLACE(l.LABEL_TEXT, '2023', '{{year}}'), '23', '{{shortYear}}') 
WHERE s.LABEL_CODE IN (
    'static.modelingValidation.fyJan', 
    'static.modelingValidation.fyFeb', 
    'static.modelingValidation.fyMar', 
    'static.modelingValidation.fyApr', 
    'static.modelingValidation.fyMay', 
    'static.modelingValidation.fyJun'
);
