package com.more.app.entity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import org.jspecify.annotations.Nullable;
import org.springframework.core.io.Resource;

import com.more.app.entity.enums.ModuleName;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "AppResource", uniqueConstraints =
{ @UniqueConstraint(columnNames =
		{ "name", "displayName" }) })

public class AppResource extends AbstractPojo implements Resource
{
	private static final long serialVersionUID = 8183700943835996275L;

	private String name;

	private String description;

	private String displayName;

	private String action;

	@Basic
	private ModuleName moduleName;

	public String getIdentifier()
	{
		return getId().toString();
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	public String getDisplayName()
	{
		return this.displayName;
	}

	public ModuleName getModuleName()
	{
		return this.moduleName;
	}

	public void setModuleName(ModuleName moduleName)
	{
		this.moduleName = moduleName;
	}

	public String getAction()
	{
		return this.action;
	}

	public void setAction(String action)
	{
		this.action = action;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public URL getURL() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI getURI() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getFile() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long contentLength() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long lastModified() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Resource createRelative(String relativePath) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable String getFilename() {
		// TODO Auto-generated method stub
		return null;
	}
}
