package co.edu.usbcali.banco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.banco.domain.Usuario;
import co.edu.usbcali.banco.repository.TipoUsuarioRepository;
import co.edu.usbcali.banco.repository.UsuarioRepository;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

@Service
@Scope("singleton")
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TipoUsuarioRepository tipoUsuarioRepository;
	
	@Autowired
	private Validator validator;

	public void validar(Usuario usuario) throws Exception {
		// TODO Auto-generated method stub
		try {
	        Set<ConstraintViolation<Usuario>> constraintViolations = validator.validate(usuario);

	        if (constraintViolations.size() > 0) {
	            StringBuilder strMessage = new StringBuilder();

	            for (ConstraintViolation<Usuario> constraintViolation : constraintViolations) {
	                strMessage.append(constraintViolation.getPropertyPath()
	                                                     .toString());
	                strMessage.append(" - ");
	                strMessage.append(constraintViolation.getMessage());
	                strMessage.append(". \n");
	            }

	            throw new Exception(strMessage.toString());
	        }
	    } catch (Exception e) {
	        throw e;
	    }

	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void save(Usuario usuario) throws Exception {
		if(usuario==null) {
			throw new Exception("El usuario es nulo");
		}
		validar(usuario);
		
		Usuario entity=usuarioRepository.findByUsuario(usuario.getUsuUsuario());
		if(entity!=null) {
			throw new Exception("El usuario no se puede crear porque ya existe");
		}
		
		usuarioRepository.save(usuario);

	}
	
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void update(Usuario usuario) throws Exception {
		if(usuario==null) {
			throw new Exception("El usuario es nulo");
		}
		validar(usuario);
		
		Usuario entity=usuarioRepository.findByUsuario(usuario.getUsuUsuario());
		if(entity==null) {
			throw new Exception("El usuario no se puede crear porque ya existe");
		}
		
		usuarioRepository.update(usuario);

	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void delete(Usuario usuario) throws Exception {
		if(usuario==null) {
			throw new Exception("El Usuario es nulo");
		}
		Usuario entity=usuarioRepository.findByUsuario(usuario.getUsuUsuario());
		usuarioRepository.delete(entity);

	}

	@Override
	@Transactional(readOnly=true)
	public Usuario findByUsuario(String usuUsuario) throws Exception {
		return usuarioRepository.findByUsuario(usuUsuario);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

}
