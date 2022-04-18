package com.mdp.autocops.service.framework;

import com.mdp.autocops.model.entity.InstitutionConfig;
import com.mdp.autocops.model.entity.ServiceEntity;
import com.mdp.autocops.model.integration.Product;

import java.util.List;

public interface InstitutionConfigService {

    InstitutionConfig create(long id, Integer reading_line, String import_format, String export_format, Boolean fail_on_error, Boolean active,
                             long service_id, String import_path, String export_path, String template_path, String reading_root, String writing_root,
                             Integer last_lines, String import_date, String export_date, String product_id, String file_prefix);

    InstitutionConfig put(long id, Integer reading_line, String import_format, String export_format, Boolean fail_on_error, Boolean active,
                          long service_id, String import_path, String export_path, String template_path, String reading_root, String writing_root,
                          Integer last_lines, String import_date, String export_date, String product_id, String file_prefix);

    List<InstitutionConfig> getAll();

    InstitutionConfig getById(long id);

    InstitutionConfig delete(long id);

    List<InstitutionConfig> getByInst(long id);

    List<ServiceEntity> getAvailableServices(long id);

    List<Product> getInstProducts(long inst_id);

}
