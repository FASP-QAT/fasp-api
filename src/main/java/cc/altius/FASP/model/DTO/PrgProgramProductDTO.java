/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

/**
 *
 * @author altius
 */
public class PrgProgramProductDTO {

    private int programProductId;
    private PrgProductDTO product;
    private int minMonths;
    private int maxMonths;

    public PrgProgramProductDTO() {
        this.product=new PrgProductDTO();
    }

    public int getProgramProductId() {
        return programProductId;
    }

    public void setProgramProductId(int programProductId) {
        this.programProductId = programProductId;
    }

    public PrgProductDTO getProduct() {
        return product;
    }

    public void setProduct(PrgProductDTO product) {
        this.product = product;
    }

    public int getMinMonths() {
        return minMonths;
    }

    public void setMinMonths(int minMonths) {
        this.minMonths = minMonths;
    }

    public int getMaxMonths() {
        return maxMonths;
    }

    public void setMaxMonths(int maxMonths) {
        this.maxMonths = maxMonths;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.programProductId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PrgProgramProductDTO other = (PrgProgramProductDTO) obj;
        if (this.programProductId != other.programProductId) {
            return false;
        }
        return true;
    }

}
